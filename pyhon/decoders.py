def get_A(message):
    return int(message.replace(" ", "")[0:2], 16)


def get_B(message):
    return int(message.replace(" ", "")[2:4], 16)


def get_C(message):
    return int(message.replace(" ", "")[4:6], 16)


def get_D(message):
    return int(message.replace(" ", "")[6:8], 16)


def get_AB(message):
    return int(message.replace(" ", "")[0:4], 16)


def get_CD(message):
    return int(message.replace(" ", "")[4:8], 16)


def get_bits(message):
    integer = int(message.replace(" ", ""), 16)
    return [bool((integer >> n) & 1) for n in range(31, -1, -1)]


def pid_getter(message):
    return [False] + get_bits(message)  # 33 bool: сам датчик + 31 +  следующий (= 1)


def default(message):
    return message


def percent(message):
    A = get_A(message)
    return A * 100 / 255


def percent_centered(message):
    A = get_A(message)
    return (A - 128) * 100 / 128


def temperature(message):
    A = get_A(message)
    return A + 233


def fuel_pressure(message):
    A = get_A(message)
    return A * 3


def rpm(message):
    AB = get_AB(message)
    return AB / 4


def timing_advance(message):
    A = get_A(message)
    return A / 2 - 64


def air_flow_rate(message):
    AB = get_AB(message)
    return AB / 100


def sensor_voltage(message):
    A, B = get_A(message), get_B(message)
    return A / 200, (B - 128) * 100 / 128  # TODO if B==$FF, sensor is not used in trim calc


def pressure_relative_to_manifold(message):
    AB = get_AB(message)
    return AB * 0.079


def pressure_diesel(message):
    AB = get_AB(message)
    return AB * 10


def sensor_voltage(message):
    AB = get_AB(message)
    CD = get_CD(message)
    return AB * 2 / 65535, CD * 8 / 65535


def evap_pressure(message):
    A, B = get_A(message), get_B(message)
    return ((A * 256) + B) / 4


def sensor_pressure(message):
    AB = get_AB(message)
    CD = get_CD(message)
    return AB / 32, 768, CD / 256 - 128


def sensor_temperature(message):
    AB = get_AB(message)
    return AB / 10 + 233


def module_voltage(message):
    AB = get_AB(message)
    return AB / 1000


def absolute_load(message):
    AB = get_AB(message)
    return AB * 100 / 255


def air_fuel_ratio(message):
    AB = get_AB(message)
    return AB / 32768


def max_values(message):
    A = get_A(message)
    B = get_B(message)
    C = get_C(message)
    D = get_D(message)
    return A, B, C, D * 10


def max_maf(message):
    A = get_A(message)
    return A * 10


def abs_evap_pressure(message):
    AB = get_AB(message)
    return AB / 200


def evap_pressure_alt(message):
    AB = get_AB(message)
    return AB - 32767


def two_percent_centered(message):
    A, B = get_A(message), get_B(message)
    return (A - 128) * 100 / 128, (B - 128) * 100 / 128


def fuel_rail_pressure(message):
    AB = get_AB(message)
    return AB * 10


def inject_timing(message):
    AB = get_AB(message)
    return (AB - 26880) / 128


def fuel_rate(message):
    AB = get_AB(message)
    return AB * 0.05


def get_4bit_banks(message):
    integer = int(message.replace(" ", "")[0:2], 16)
    return [bool((integer >> n) & 1) for n in range(7, 3, -1)], [bool((integer >> n) & 1) for n in range(3, -1, -1)]


def get_2bit_banks(message):
    integer = int(message.replace(" ", "")[0:2], 16)
    return [bool((integer >> n) & 1) for n in range(7, 5, -1)], [bool((integer >> n) & 1) for n in range(5, 3, -1)], [
        bool((integer >> n) & 1) for n in range(3, 1, -1)], [bool((integer >> n) & 1) for n in range(1, -1, -1)]


def get_first_bit(message):
    integer = int(message.replace(" ", "")[0:2], 16)
    return bool((integer >> 7) & 1)
