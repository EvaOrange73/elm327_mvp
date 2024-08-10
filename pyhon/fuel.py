import time
import warnings
from bleak import *
import commands

warnings.filterwarnings('ignore')
# ADDRESS = "00:10:CC:4F:36:03"  # синий
ADDRESS = ""  # красный
READ_UUID = "FFF1"
WRITE_UUID = "FFF2"


async def main():
    async with BleakClient(ADDRESS) as client:
        async def send(string, sleep=0.5):
            await client.write_gatt_char(WRITE_UUID, bytearray(string, "utf-8"))
            await asyncio.sleep(sleep)

        async def get_data(info, text):
            global cur_maf, cur_map, cur_rpm, cur_iat
            string = str(text, "utf-8")
            output = ""
            for ch in string:
                if ch != '\r' and ch != '>':
                    output += ch
            if output.startswith("7E9"): return
            if not output: return
            mode = output.replace(" ", "")[6:7]
            pid = output.replace(" ", "")[7:9]
            command = commands.OBDCommand.get_command_by_code(mode, pid)
            data = output.replace(" ", "")[9:9 + command.bytes * 2]
            if command != commands.DEFAULT_CODE:
                decoded = command.decoder(data)
                log = str(time.time_ns()) + " " + pid + " " + command.printer(decoded) + "\n"
                if pid == '10':
                    cur_maf = decoded
                elif pid == 'OB':
                    cur_map = decoded
                elif pid == 'OC':
                    cur_rpm = decoded
                elif pid == 'OF':
                    cur_iat =decoded
            else:
                log = output + '\n'
            print(output + "\n", sep='', end='')
            with open("logs_fuel.txt", "a") as f:
                f.write(log)

        with open("logs_fuel.txt", "w") as f:
            f.write("")

        await client.start_notify(READ_UUID, get_data)
        await send("ATZ\r", sleep=1)
        await send("ATD\r")
        await send("ATH1\r")
        await send("ATS1\r")
        await send("ATSP6\r")

        print("-----", sep='', end='\n')

        cur_time = prev_time = time.time_ns()
        cur_maf = cur_map = cur_rpm = cur_iat = 0
        liters_spent_1 = liters_spent_2 = 0
        while True:
            await send('0110\r')  # MAF
            await send('010B\r')  # MAP
            await send('010C\r')  # RPM
            await send('010F\r')  # IAT
            # cur_time = time.time_ns()
            # await asyncio.sleep(1)
            # delta_time = cur_time - prev_time
            # liters_spent_1 += delta_time * cur_maf / 12556000000
            # cur_maf = (cur_rpm * cur_map + 2 * 28.97) / (12000 * 8.314 * cur_iat)
            # liters_spent_2 += delta_time * cur_maf / 12556000000
            # print(round(liters_spent_1, 5), round(liters_spent_2, 5))
            # prev_time = cur_time


if __name__ == "__main__":
    asyncio.run(main())
