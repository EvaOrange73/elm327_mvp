import warnings
from bleak import *

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
            print(output)
            # with open("logs.txt", "a") as f:
            #     f.write(output + '\n')

        services = await client.get_services()
        for service in services:
            print("Service ", service.uuid, service.description, sep=' ', end='\n')
            for characteristic in service.characteristics:
                print(characteristic.uuid, characteristic.description, sep=' ', end='\n')
        with open("logs.txt", "w") as f:
            f.write("")
        await client.start_notify(READ_UUID, get_data)
        await send("ATZ\r", sleep=1)
        await send("ATI\r")
        await send("ATL1\r")
        await send("ATH1\r")
        await send("ATS0\r")
        await send("ATAL\r")
        await send("ATAR\r")
        #await send("ATCF140\r")
        await send("ATSP0\r", sleep=1)
        await send("ATMT\r")
        #await asyncio.sleep(100)
        for i in range(100):
            #await send("ATMA\r")
            #print(await client.read_gatt_char(READ_UUID))
            await asyncio.sleep(1)


if __name__ == "__main__":
    asyncio.run(main())
