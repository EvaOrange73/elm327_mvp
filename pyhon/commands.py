import enum

import decoders
import printers


class OBDCommand:
    def __init__(self,
                 desc,
                 pid,
                 _bytes,
                 decoder,
                 printer):
        self.desc = desc  # human readable description
        self.pid = pid  # command string
        self.bytes = _bytes  # number of bytes expected in return
        self.decoder = decoder  # decoding function
        self.printer = printer

    @staticmethod
    def get_command_by_code(mode, pid):
        for key in list(commands.keys()):
            if key.value == "0" + mode:
                for command in commands[key]:
                    if command.pid == pid:
                        return command
        return DEFAULT_CODE


DEFAULT_CODE = OBDCommand("", "", 0, decoders.default, printers.default)


class MODE_CODES(enum.Enum):
    CURRENT_DATA = "01"
    FREEZE_FRAME_DATA = "02"
    SHOW_CODES = "03"
    CLEAR_CODES = "04"
    TEST_OXYGEN = "05"
    TEST_OTHER = "06"
    SHOW_PENDING = "07"
    CONTROL_OPERATION = "08"
    VEHICLE_INFORMATION = "09"
    PERMANENT_CODES = "0A"


commands = {MODE_CODES.CURRENT_DATA: [
    OBDCommand("Supported PIDs [01-20]", "00", 4, decoders.pid_getter, printers.default),
    OBDCommand("Status since DTCs cleared", "01", 6, decoders.default, printers.default),  # TODO
    OBDCommand("DTC that triggered the freeze frame", "02", 4, decoders.default, printers.default),  # TODO
    OBDCommand("Fuel System Status", "03", 4, decoders.default, printers.default),  # TODO
    OBDCommand("Calculated Engine Load", "04", 3, decoders.percent, printers.percentage),
    OBDCommand("Engine Coolant Temperature", "05", 3, decoders.temperature, printers.temperature),
    OBDCommand("Short Term Fuel Trim - Bank 1", "06", 3, decoders.percent_centered, printers.percentage),
    OBDCommand("Long Term Fuel Trim - Bank 1", "07", 3, decoders.percent_centered, printers.percentage),
    OBDCommand("Short Term Fuel Trim - Bank 2", "08", 3, decoders.percent_centered, printers.percentage),
    OBDCommand("Long Term Fuel Trim - Bank 2", "09", 3, decoders.percent_centered, printers.percentage),
    OBDCommand("Fuel Pressure", "0A", 3, decoders.fuel_pressure, printers.pressure_kPa),
    OBDCommand("Intake Manifold Pressure", "0B", 3, decoders.get_A, printers.pressure_kPa),
    OBDCommand("Engine RPM", "0C", 4, decoders.rpm, printers.engine_speed),
    OBDCommand("Vehicle Speed", "0D", 3, decoders.get_A, printers.speed),
    OBDCommand("Timing Advance", "0E", 3, decoders.timing_advance, printers.angle),
    OBDCommand("Intake Air Temp", "0F", 3, decoders.temperature, printers.temperature),
    OBDCommand("Air Flow Rate (MAF)", "10", 4, decoders.air_flow_rate, printers.flow),
    OBDCommand("Throttle Position", "11", 3, decoders.percent, printers.percentage),
    OBDCommand("Secondary Air Status", "12", 3, decoders.default, printers.default),  # TODO
    OBDCommand("O2 Sensors Present", "13", 3, decoders.get_4bit_banks, printers.default),
    OBDCommand("O2: Bank 1 - Sensor 1 Voltage", "14", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 1 - Sensor 2 Voltage", "15", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 1 - Sensor 3 Voltage", "16", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 1 - Sensor 4 Voltage", "17", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 2 - Sensor 1 Voltage", "18", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 2 - Sensor 2 Voltage", "19", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 2 - Sensor 3 Voltage", "1A", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("O2: Bank 2 - Sensor 4 Voltage", "1B", 4, decoders.sensor_voltage, printers.voltage_and_percentage),
    OBDCommand("OBD Standards Compliance", "1C", 3, decoders.default, printers.default),  # TODO
    OBDCommand("O2 Sensors Present (alternate)", "1D", 3, decoders.get_2bit_banks, printers.default),
    OBDCommand("Auxiliary input status (power take off)", "1E", 3, decoders.get_first_bit, printers.default),
    OBDCommand("Engine Run Time", "1F", 4, decoders.get_AB, printers.time),

    OBDCommand("Supported PIDs [21-40]", "20", 4, decoders.pid_getter, printers.default),
    OBDCommand("Distance Traveled with MIL on", "21", 4, decoders.get_AB, printers.distance),
    OBDCommand("Fuel Rail Pressure (relative to vacuum)", "22", 4, decoders.pressure_relative_to_manifold,
               printers.pressure_kPa),
    OBDCommand("Fuel Rail Pressure (direct inject)", "23", 4, decoders.pressure_diesel, printers.pressure_kPa),
    OBDCommand("02 Sensor 1 WR Lambda Voltage", "24", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    # TODO : ((A*256)+B)*2/65535 ????or????? ((A*256)+B)/32768
    OBDCommand("02 Sensor 2 WR Lambda Voltage", "25", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 3 WR Lambda Voltage", "26", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 4 WR Lambda Voltage", "27", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 5 WR Lambda Voltage", "28", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 6 WR Lambda Voltage", "29", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 7 WR Lambda Voltage", "2A", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("02 Sensor 8 WR Lambda Voltage", "2B", 6, decoders.sensor_voltage, printers.ratio_and_voltage),
    OBDCommand("Commanded EGR", "2C", 3, decoders.percent, printers.percentage),
    OBDCommand("EGR Error", "2D", 3, decoders.percent_centered, printers.percentage),
    OBDCommand("Commanded Evaporative Purge", "2E", 3, decoders.percent, printers.percentage),
    OBDCommand("Fuel Level Input", "2F", 3, decoders.percent, printers.percentage),
    OBDCommand("Number of warm-ups since codes cleared", "30", 3, decoders.get_A, printers.default),
    OBDCommand("Distance traveled since codes cleared", "31", 4, decoders.get_AB, printers.distance),
    OBDCommand("Evaporative system vapor pressure", "32", 4, decoders.evap_pressure, printers.pressure_Pa),
    OBDCommand("Barometric Pressure", "33", 3, decoders.get_A, printers.pressure_kPa),
    OBDCommand("02 Sensor 1 WR Lambda Current", "34", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 2 WR Lambda Current", "35", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 3 WR Lambda Current", "36", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 4 WR Lambda Current", "37", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 5 WR Lambda Current", "38", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 6 WR Lambda Current", "39", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 7 WR Lambda Current", "3A", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("02 Sensor 8 WR Lambda Current", "3B", 6, decoders.sensor_pressure, printers.default),
    OBDCommand("Catalyst Temperature: Bank 1 - Sensor 1", "3C", 4, decoders.sensor_temperature, printers.default),
    OBDCommand("Catalyst Temperature: Bank 2 - Sensor 1", "3D", 4, decoders.sensor_temperature, printers.default),
    OBDCommand("Catalyst Temperature: Bank 1 - Sensor 2", "3E", 4, decoders.sensor_temperature, printers.default),
    OBDCommand("Catalyst Temperature: Bank 2 - Sensor 2", "3F", 4, decoders.sensor_temperature, printers.default),

    OBDCommand("Supported PIDs [41-60]", "40", 4, decoders.pid_getter, printers.default),
    OBDCommand("Monitor status this drive cycle", "41", 6, decoders.get_bits, printers.default),
    OBDCommand("Control module voltage", "42", 4, decoders.module_voltage, printers.default),
    OBDCommand("Absolute load value", "43", 4, decoders.absolute_load, printers.default),
    OBDCommand("Commanded equivalence ratio", "44", 4, decoders.air_fuel_ratio, printers.default),
    OBDCommand("Relative throttle position", "45", 3, decoders.percent, printers.percentage),
    OBDCommand("Ambient air temperature", "46", 3, decoders.temperature, printers.temperature),
    OBDCommand("Absolute throttle position B", "47", 3, decoders.percent, printers.percentage),
    OBDCommand("Absolute throttle position C", "48", 3, decoders.percent, printers.percentage),
    OBDCommand("Accelerator pedal position D", "49", 3, decoders.percent, printers.percentage),
    OBDCommand("Accelerator pedal position E", "4A", 3, decoders.percent, printers.percentage),
    OBDCommand("Accelerator pedal position F", "4B", 3, decoders.percent, printers.percentage),
    OBDCommand("Commanded throttle actuator", "4C", 3, decoders.percent, printers.percentage),
    OBDCommand("Time run with MIL on", "4D", 4, decoders.get_AB, printers.default),
    OBDCommand("Time since trouble codes cleared", "4E", 4, decoders.get_AB, printers.default),
    OBDCommand("Maximum value for equivalence ratio, "
               "oxygen sensor voltage, oxygen sensor current, "
               "and intake manifold absolute pressure", "4F", 6, decoders.max_values, printers.default),
    OBDCommand("Maximum value for mass air flow sensor", "50", 6, decoders.max_maf, printers.default),
    OBDCommand("Fuel Type", "51", 3, decoders.default, printers.default),  # TODO
    OBDCommand("Ethanol Fuel Percent", "52", 3, decoders.percent, printers.percentage),
    OBDCommand("Absolute Evap system Vapor Pressure", "53", 4, decoders.abs_evap_pressure, printers.default),
    OBDCommand("Evap system vapor pressure", "54", 4, decoders.evap_pressure_alt, printers.default),
    OBDCommand("Short term secondary O2 trim - Bank 1", "55", 4, decoders.two_percent_centered, printers.percentage),
    OBDCommand("Long term secondary O2 trim - Bank 1", "56", 4, decoders.two_percent_centered, printers.percentage),
    OBDCommand("Short term secondary O2 trim - Bank 2", "57", 4, decoders.two_percent_centered, printers.percentage),
    OBDCommand("Long term secondary O2 trim - Bank 2", "58", 4, decoders.two_percent_centered, printers.percentage),
    OBDCommand("Fuel rail pressure (absolute)", "59", 4, decoders.fuel_rail_pressure, printers.default),
    OBDCommand("Relative accelerator pedal position", "5A", 3, decoders.percent, printers.percentage),
    OBDCommand("Hybrid battery pack remaining life", "5B", 3, decoders.percent, printers.percentage),
    OBDCommand("Engine oil temperature", "5C", 3, decoders.temperature, printers.temperature),
    OBDCommand("Fuel injection timing", "5D", 4, decoders.inject_timing, printers.default),
    OBDCommand("Engine fuel rate", "5E", 4, decoders.fuel_rate, printers.default),
    OBDCommand("Designed emission requirements", "5F", 3, decoders.default, printers.default),  # TODO
]
}

# # mode 2 is the same as mode 1, but returns values from when the DTC occured
# __mode2__ = []
# for c in __mode1__:
#     c = c.clone()
#     c.command = "" + c.command[2:]  # change the mode: 0100 ---> 0200
#     c.name = "DTC_" + c.name
#     c.desc = "DTC " + c.desc
#     if c.decoder == decoders.pid:
#         c.decoder = decoders.drop  # Never send mode 02 pid requests (use mode 01 instead)
#     __mode2__.append(c)
#
# __mode3__ = [
#     OBDCommand("Get DTCs", "", 0, decoders.dtc, printers.default),
# ]
#
# __mode4__ = [
#     OBDCommand("Clear DTCs and Freeze data", "", 0, decoders.drop, printers.default),
# ]
#
# __mode6__ = [
#     # Mode 06 calls PID's MID's (Monitor ID)
#     # This is for CAN only
#     #                      name                             description                            cmd     bytes       decoder           ECU        fast
#     OBDCommand("Supported MIDs [01-20]"                         , "00",   0, decoders.pid, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 1 - Sensor 1"            , "01",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 1 - Sensor 2"            , "02",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 1 - Sensor 3"            , "03",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 1 - Sensor 4"            , "04",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 2 - Sensor 1"            , "05",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 2 - Sensor 2"            , "06",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 2 - Sensor 3"            , "07",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 2 - Sensor 4"            , "08",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 3 - Sensor 1"            , "09",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 3 - Sensor 2"            , "0A",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 3 - Sensor 3"            , "0B",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 3 - Sensor 4"            , "0C",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 4 - Sensor 1"            , "0D",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 4 - Sensor 2"            , "0E",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 4 - Sensor 3"            , "0F",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Monitor Bank 4 - Sensor 4"            , "10",   0, decoders.monitor, printers.default),
# ] + ([None] * 15) + [ # 11 - 1F Reserved
#     OBDCommand("Supported MIDs [21-40]"                         , "20",   0, decoders.pid, printers.default),
#     OBDCommand("Catalyst Monitor Bank 1"                        , "21",   0, decoders.monitor, printers.default),
#     OBDCommand("Catalyst Monitor Bank 2"                        , "22",   0, decoders.monitor, printers.default),
#     OBDCommand("Catalyst Monitor Bank 3"                        , "23",   0, decoders.monitor, printers.default),
#     OBDCommand("Catalyst Monitor Bank 4"                        , "24",   0, decoders.monitor, printers.default),
# ] + ([None] * 12) + [ # 25 - 30 Reserved
#     OBDCommand("EGR Monitor Bank 1"                             , "31",   0, decoders.monitor, printers.default),
#     OBDCommand("EGR Monitor Bank 2"                             , "32",   0, decoders.monitor, printers.default),
#     OBDCommand("EGR Monitor Bank 3"                             , "33",   0, decoders.monitor, printers.default),
#     OBDCommand("EGR Monitor Bank 4"                             , "34",   0, decoders.monitor, printers.default),
#     OBDCommand("VVT Monitor Bank 1"                             , "35",   0, decoders.monitor, printers.default),
#     OBDCommand("VVT Monitor Bank 2"                             , "36",   0, decoders.monitor, printers.default),
#     OBDCommand("VVT Monitor Bank 3"                             , "37",   0, decoders.monitor, printers.default),
#     OBDCommand("VVT Monitor Bank 4"                             , "38",   0, decoders.monitor, printers.default),
#     OBDCommand("EVAP Monitor (Cap Off / 0.150\")"               , "39",   0, decoders.monitor, printers.default),
#     OBDCommand("EVAP Monitor (0.090\")"                         , "3A",   0, decoders.monitor, printers.default),
#     OBDCommand("EVAP Monitor (0.040\")"                         , "3B",   0, decoders.monitor, printers.default),
#     OBDCommand("EVAP Monitor (0.020\")"                         , "3C",   0, decoders.monitor, printers.default),
#     OBDCommand("Purge Flow Monitor"                             , "3D",   0, decoders.monitor, printers.default),
# ] + ([None] * 2) + [ # 3E - 3F Reserved
#     OBDCommand("Supported MIDs [41-60]"                         , "40",   0, decoders.pid, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 1 - Sensor 1"     , "41",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 1 - Sensor 2"     , "42",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 1 - Sensor 3"     , "43",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 1 - Sensor 4"     , "44",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 2 - Sensor 1"     , "45",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 2 - Sensor 2"     , "46",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 2 - Sensor 3"     , "47",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 2 - Sensor 4"     , "48",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 3 - Sensor 1"     , "49",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 3 - Sensor 2"     , "4A",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 3 - Sensor 3"     , "4B",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 3 - Sensor 4"     , "4C",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 4 - Sensor 1"     , "4D",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 4 - Sensor 2"     , "4E",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 4 - Sensor 3"     , "4F",   0, decoders.monitor, printers.default),
#     OBDCommand("O2 Sensor Heater Monitor Bank 4 - Sensor 4"     , "50",   0, decoders.monitor, printers.default),
# ] + ([None] * 15) + [ # 51 - 5F Reserved
#     OBDCommand("Supported MIDs [61-80]"                         , "60",   0, decoders.pid, printers.default),
#     OBDCommand("Heated Catalyst Monitor Bank 1"                 , "61",   0, decoders.monitor, printers.default),
#     OBDCommand("Heated Catalyst Monitor Bank 2"                 , "62",   0, decoders.monitor, printers.default),
#     OBDCommand("Heated Catalyst Monitor Bank 3"                 , "63",   0, decoders.monitor, printers.default),
#     OBDCommand("Heated Catalyst Monitor Bank 4"                 , "64",   0, decoders.monitor, printers.default),
# ] + ([None] * 12) + [ # 65 - 70 Reserved
#     OBDCommand("Secondary Air Monitor 1"                        , "71",   0, decoders.monitor, printers.default),
#     OBDCommand("Secondary Air Monitor 2"                        , "72",   0, decoders.monitor, printers.default),
#     OBDCommand("Secondary Air Monitor 3"                        , "73",   0, decoders.monitor, printers.default),
#     OBDCommand("Secondary Air Monitor 4"                        , "74",   0, decoders.monitor, printers.default),
# ] + ([None] * 11) + [ # 75 - 7F Reserved
#     OBDCommand("Supported MIDs [81-A0]"                         , "80",   0, decoders.pid, printers.default),
#     OBDCommand("Fuel System Monitor Bank 1"                     , "81",   0, decoders.monitor, printers.default),
#     OBDCommand("Fuel System Monitor Bank 2"                     , "82",   0, decoders.monitor, printers.default),
#     OBDCommand("Fuel System Monitor Bank 3"                     , "83",   0, decoders.monitor, printers.default),
#     OBDCommand("Fuel System Monitor Bank 4"                     , "84",   0, decoders.monitor, printers.default),
#     OBDCommand("Boost Pressure Control Monitor Bank 1"          , "85",   0, decoders.monitor, printers.default),
#     OBDCommand("Boost Pressure Control Monitor Bank 1"          , "86",   0, decoders.monitor, printers.default),
# ] + ([None] * 9) + [ # 87 - 8F Reserved
#     OBDCommand("NOx Absorber Monitor Bank 1"                    , "90",   0, decoders.monitor, printers.default),
#     OBDCommand("NOx Absorber Monitor Bank 2"                    , "91",   0, decoders.monitor, printers.default),
# ] + ([None] * 6) + [ # 92 - 97 Reserved
#     OBDCommand("NOx Catalyst Monitor Bank 1"                    , "98",   0, decoders.monitor, printers.default),
#     OBDCommand("NOx Catalyst Monitor Bank 2"                    , "99",   0, decoders.monitor, printers.default),
# ] + ([None] * 6) + [ # 9A - 9F Reserved
#     OBDCommand("Supported MIDs [A1-C0]"                         , "A0",   0, decoders.pid, printers.default),
#     OBDCommand("Misfire Monitor General Data"                   , "A1",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 1 Data"                        , "A2",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 2 Data"                        , "A3",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 3 Data"                        , "A4",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 4 Data"                        , "A5",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 5 Data"                        , "A6",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 6 Data"                        , "A7",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 7 Data"                        , "A8",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 8 Data"                        , "A9",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 9 Data"                        , "AA",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 10 Data"                       , "AB",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 11 Data"                       , "AC",   0, decoders.monitor, printers.default),
#     OBDCommand("Misfire Cylinder 12 Data"                       , "AD",   0, decoders.monitor, printers.default),
# ] + ([None] * 2) + [ # AE - AF Reserved
#     OBDCommand("PM Filter Monitor Bank 1"                       , "B0",   0, decoders.monitor, printers.default),
#     OBDCommand("PM Filter Monitor Bank 2"                       , "B1",   0, decoders.monitor, printers.default),
# ]
#
# __mode7__ = [
#     OBDCommand("Get DTCs from the current/last driving cycle", "", 0, decoders.dtc, printers.default),
# ]
#
#
# __mode9__ = [
#     #                      name                             description                            cmd     bytes       decoder       ECU        fast
#     OBDCommand("Supported PIDs [01-20]"                            , "00",  7, decoders.pid, printers.default),
#     OBDCommand("VIN Message Count"                                 , "01",  3, decoders.count, printers.default),
#     OBDCommand("Vehicle Identification Number"                     , "02", 22, decoders.encoded_string(17), printers.default),
#     OBDCommand("03",  3, decoders.count, printers.default),
#     OBDCommand("Calibration ID"                                    , "04", 18, decoders.encoded_string(16), printers.default),
#     OBDCommand("CVN Message Count for PID 06"                      , "05",  3, decoders.count, printers.default),
#     OBDCommand("Calibration Verification Numbers"                  , "06", 10, decoders.cvn, printers.default),
#
# #
# # NOTE: The following are untested
# #
# #    OBDCommand("Performance tracking message count"                , "07",  3, decoders.count, printers.default),
# #    OBDCommand("In-use performance tracking (spark ignition)"      , "08",  4, decoders.raw_string, printers.default),
# #    OBDCommand("ECU Name Message Count for PID 0A"                 , "09",  3, decoders.count, printers.default),
# #    OBDCommand("ECU Name"                                          , "0a", 20, decoders.raw_string, printers.default),
# #    OBDCommand("In-use performance tracking (compression ignition)", "0b",  4, decoders.raw_string, printers.default),
# ]
