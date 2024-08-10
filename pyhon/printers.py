def default(value):
    return str(value)


def percentage(value):
    return str(value) + "%"


def voltage(value):
    return str(value) + " V"


def temperature(value):
    return str(value) + " K"


def pressure_kPa(value):
    return str(value) + " kPa"


def pressure_Pa(value):
    return str(value) + " Pa"


def engine_speed(value):
    return str(value) + " rpm"


def speed(value):
    return str(value) + " km/h"


def angle(value):
    return str(value) + "°"


def flow(value):
    return str(value) + " g/s"


def voltage_and_percentage(value):
    return str(value[0]) + " V, " + str(value[1]) + "%"


def time(value):
    return str(value) + " sec"


def distance(value):
    return str(value) + " km"


def ratio_and_voltage(value):
    return str(value[0]) + " (ratio), " + str(value[1]) + "V"
