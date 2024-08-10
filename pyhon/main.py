import warnings
from bleak import *
import commands

warnings.filterwarnings('ignore')
# ADDRESS = "00:10:CC:4F:36:03"  # синий
ADDRESS = "22:C0:00:03:61:2E"  # красный
READ_UUID = "FFF1"
WRITE_UUID = "FFF2"


async def main():
    async with BleakClient(ADDRESS) as client:
        async def send(string, sleep=0.5):
            await client.write_gatt_char(WRITE_UUID, bytearray(string, "utf-8"))
            await asyncio.sleep(sleep)

        async def get_data(info, text):
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
                if int(command.pid, 16) % 32 == 0:
                    for i in range(len(decoded)):
                        if_supported_pids[int(command.pid, 16) + i] = decoded[i]
                log = output + " | " + command.desc + " : " + command.printer(decoded) + "\n"
            else:
                log = output + '\n'
            print(output + "\n", sep='', end='')
            with open("logs.txt", "a") as f:
                f.write(log)

        services = await client.get_services()
        for service in services:
            print("Service ", service.uuid, service.description, sep=' ', end='\n')
            for characteristic in service.characteristics:
                print(characteristic.uuid, characteristic.description, sep=' ', end='\n')
        with open("logs.txt", "w") as f:
            f.write("")
        await client.start_notify(READ_UUID, get_data)
        # await send("ATZ\r", sleep=1)
        # # await send("ATD\r")
        # await send("ATH1\r")
        # await send('AT SH 7E0\r')
        # await send("ATSP6\r")
        # await send("ATDP\r")
        # await send("ATRV\r")
        # # await send("ATDP\r")
        # # await send("ATKW\r")

        await send("ATZ\r", sleep=1)
        await send("ATE0\r")
        await send("ATE0\r")
        await send("ATE0\r")
        await send("STI\r")
        await send("VTI\r")
        await send("ATD\r")
        await send("ATD0\r")
        await send("ATE0\r")
        await send("ATH1\r")
        await send("ATSP0\r", sleep=10)
        await send("ATE0\r")
        await send("ATH1\r")
        await send("ATM0\r")
        await send("ATS0\r")
        await send("ATAT1\r")
        await send("ATAL\r")
        await send("ATST64\r", sleep=10)

        print("-----", sep='', end='\n')

        if_supported_pids = [False for i in range(100)]
        supported_pids = []
        if_supported_pids[0] = True
        await send('0100\r')
        await send('0120\r')
        await send('0140\r')
        for i in range(len(if_supported_pids)):
            if if_supported_pids[i] and i % 32 != 0:
                print(hex(i), end=' ')
                supported_pids.append(i)
        for i in range(60):
            for pid in supported_pids:
                await send('01' + f"{pid:0{2}X}" + '\r')
            await asyncio.sleep(1)


if __name__ == "__main__":
    asyncio.run(main())
