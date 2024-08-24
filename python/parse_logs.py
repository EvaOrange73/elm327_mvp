import re
import matplotlib.pyplot as plt
from numpy import mean

maf_sum = 0
maf_count = 0

prev_time = 0
cur_time = 0

gps_sum = 0

x = []
y = []

times = [1722887141]
maps = [[]]
rpms = [[]]
iats = [[]]

last_time = 1722887141
last_map = 0
last_rpm = 0
last_iat = 0
last_sum_gps = 0

last_x = []
last_y = []

with open("logs_fuel_1.txt", "r") as f:
    while line := f.readline():
        if re.match("\d{10}", line):
            tmp = line.split(" ")
            time = tmp[0]
            cur_time = int(time) / 10 ** 9
            pid = tmp[1]
            value = tmp[2].replace("\n", "")
            if value[-1] == "K":
                value = value[:-1]
            if pid == "10":
                maf_sum += float(value)
                maf_count += 1
                x.append(cur_time)
                y.append(float(value))
                if (prev_time != 0):
                    gps_sum += float(value) * (cur_time - prev_time) / 14.6 / 0.86 / 1000
            else:
                if cur_time > times[-1] + 5:
                    times.append(times[-1] + 5)
                    maps.append([])
                    rpms.append([])
                    iats.append([])
                if pid == "0B":
                    maps[-1].append(float(value))
                    last_map = float(value)
                elif pid == "0C":
                    rpms[-1].append(float(value))
                    last_rpm = float(value)
                elif pid == "0F":
                    iats[-1].append(float(value))
                    last_iat = float(value)

                if cur_time > last_time + 1 and last_iat:
                    last_maf = (last_rpm * last_map * 1 + 28.97 * 2) / (60 * 100 * last_iat * 8.314 * 2)

                    last_sum_gps += (cur_time - last_time) * last_maf / 14.6 / 0.86
                    last_x.append(cur_time)
                    last_y.append(last_maf)
                    last_time = cur_time


            prev_time = cur_time
            # print(cur_time, pid)

# print(maf_sum / maf_count / 14.6 / 0.86 / 1000 * 360 / 3)
print(gps_sum)
print(last_sum_gps)


# plt.plot(x, y)
# plt.show()

x = []
y = []
gps_sum = 0
for i in range(len(times)):
    if len(maps[i]) and len(rpms[i]) and len(iats[i]):
        x.append(times[i])
        maf = (mean(rpms[i]) * mean(maps[i]) * 1 + 28.97 * 2) / (60 * 100 * mean(iats[i]) * 8.314 * 2)
        y.append(maf)
        gps_sum += maf * 5 / 14.6 / 0.86


plt.plot(last_x, last_y)
plt.show()
print(gps_sum)

print(times)
print(maps)
print(rpms)
print(iats)
