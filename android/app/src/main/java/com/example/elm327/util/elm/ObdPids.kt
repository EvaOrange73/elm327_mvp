package com.example.elm327.util.elm

import android.util.Log
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.value.*
import java.sql.Timestamp

enum class ObdPids(val pid: String, val descriptionShort: String, val descriptionLong: String, val decoder: Decoders) {
    NO_PID_FOUND("", "", "", Decoders.DEFAULT),
    PID_00("00", "", "Supported PIDs [01-20]", Decoders.PIDS_01_20),
    PID_01("01", "", "Status since DTCs cleared", Decoders.MONITOR_STATUS),
    PID_02("02", "", "DTC that triggered the freeze frame", Decoders.DTC_TRIGGERED),
    PID_03("03", "", "Fuel System Status", Decoders.FUEL_SYSTEM_STATUS),
    PID_04("04", "", "Calculated Engine Load", Decoders.PERCENT),
    PID_05("05", "", "Engine Coolant Temperature", Decoders.TEMPERATURE),
    PID_06("06", "", "Short Term Fuel Trim - Bank 1", Decoders.PERCENT_CENTERED),
    PID_07("07", "", "Long Term Fuel Trim - Bank 1", Decoders.PERCENT_CENTERED),
    PID_08("08", "", "Short Term Fuel Trim - Bank 2", Decoders.PERCENT_CENTERED),
    PID_09("09", "", "Long Term Fuel Trim - Bank 2", Decoders.PERCENT_CENTERED),
    PID_0A("0A", "", "Fuel Pressure", Decoders.FUEL_PRESSURE),
    PID_0B("0B", "", "Intake Manifold Pressure", Decoders.PRESSURE),
    PID_0C("0C", "", "Engine RPM", Decoders.RPM),
    PID_0D("0D", "", "Vehicle Speed", Decoders.SPEED),
    PID_0E("0E", "", "Timing Advance", Decoders.TIMING_ADVANCE),
    PID_0F("0F", "", "Intake Air Temp", Decoders.TEMPERATURE),
    PID_10("10", "", "Air Flow Rate (MAF)", Decoders.AIR_FLOW_RATE),
    PID_11("11", "", "Throttle Position", Decoders.PERCENT),
    PID_12("12", "", "Secondary Air Status", Decoders.AIR_STATUS),
    PID_13("13", "", "O2 Sensors Present", Decoders.SENSORS_1_8),
    PID_14("14", "", "O2: Bank 1 - Sensor 1 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_15("15", "", "O2: Bank 1 - Sensor 2 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_16("16", "", "O2: Bank 1 - Sensor 3 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_17("17", "", "O2: Bank 1 - Sensor 4 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_18("18", "", "O2: Bank 2 - Sensor 1 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_19("19", "", "O2: Bank 2 - Sensor 2 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_1A("1A", "", "O2: Bank 2 - Sensor 3 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_1B("1B", "", "O2: Bank 2 - Sensor 4 Voltage", Decoders.SENSOR_VOLTAGE),
    PID_1C("1C", "", "OBD Standards Compliance", Decoders.OBD_STANDARDS),
    PID_1D("1D", "", "O2 Sensors Present (alternate)", Decoders.SENSORS_1_8_ALT),
    PID_1E("1E", "", "Auxiliary input status (power take off)", Decoders.BOOLEAN),
    PID_1F("1F", "", "Engine Run Time", Decoders.RUN_TIME_SEC),
    PID_20("20", "", "Supported PIDs [21-40]", Decoders.PIDS_21_40),
    PID_21("21", "", "Distance Traveled with MIL on", Decoders.DISTANCE),
    PID_22("22", "", "Fuel Rail Pressure (relative to vacuum)", Decoders.RAIL_PRESSURE),
    PID_23("23", "", "Fuel Rail Pressure (direct inject)", Decoders.RAIL_GAUGE_PRESSURE),
    PID_24("24", "", "02 Sensor 1 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_25("25", "", "02 Sensor 2 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_26("26", "", "02 Sensor 3 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_27("27", "", "02 Sensor 4 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_28("28", "", "02 Sensor 5 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_29("29", "", "02 Sensor 6 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_2A("2A", "", "02 Sensor 7 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_2B("2B", "", "02 Sensor 8 WR Lambda Voltage", Decoders.OXYGEN_VOLTAGE),
    PID_2C("2C", "", "Commanded EGR", Decoders.PERCENT),
    PID_2D("2D", "", "EGR Error", Decoders.PERCENT_CENTERED),
    PID_2E("2E", "", "Commanded Evaporative Purge", Decoders.PERCENT),
    PID_2F("2F", "", "Fuel Level Input", Decoders.PERCENT),
    PID_30("30", "", "Number of warm-ups since codes cleared", Decoders.INTEGER),
    PID_31("31", "", "Distance traveled since codes cleared", Decoders.DISTANCE),
    PID_32("32", "", "Evaporative system vapor pressure", Decoders.EVAP_PRESSURE),
    PID_33("33", "", "Barometric Pressure", Decoders.PRESSURE),
    PID_34("34", "", "02 Sensor 1 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_35("35", "", "02 Sensor 2 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_36("36", "", "02 Sensor 3 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_37("37", "", "02 Sensor 4 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_38("38", "", "02 Sensor 5 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_39("39", "", "02 Sensor 6 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_3A("3A", "", "02 Sensor 7 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_3B("3B", "", "02 Sensor 8 WR Lambda Current", Decoders.OXYGEN_SENSOR),
    PID_3C("3C", "", "Catalyst Temperature: Bank 1 - Sensor 1", Decoders.SENSOR_TEMPERATURE),
    PID_3D("3D", "", "Catalyst Temperature: Bank 2 - Sensor 1", Decoders.SENSOR_TEMPERATURE),
    PID_3E("3E", "", "Catalyst Temperature: Bank 1 - Sensor 2", Decoders.SENSOR_TEMPERATURE),
    PID_3F("3F", "", "Catalyst Temperature: Bank 2 - Sensor 2", Decoders.SENSOR_TEMPERATURE),
    PID_40("40", "", "Supported PIDs [41-60]", Decoders.PIDS_41_60),
    PID_41("41", "", "Monitor status this drive cycle", Decoders.MONITOR_STATUS),
    PID_42("42", "", "Control module voltage", Decoders.MODULE_VOLTAGE),
    PID_43("43", "", "Absolute load value", Decoders.ABSOLUTE_LOAD),
    PID_44("44", "", "Commanded equivalence ratio", Decoders.AIR_FUEL_RATIO),
    PID_45("45", "", "Relative throttle position", Decoders.PERCENT),
    PID_46("46", "", "Ambient air temperature", Decoders.TEMPERATURE),
    PID_47("47", "", "Absolute throttle position B", Decoders.PERCENT),
    PID_48("48", "", "Absolute throttle position C", Decoders.PERCENT),
    PID_49("49", "", "Accelerator pedal position D", Decoders.PERCENT),
    PID_4A("4A", "", "Accelerator pedal position E", Decoders.PERCENT),
    PID_4B("4B", "", "Accelerator pedal position F", Decoders.PERCENT),
    PID_4C("4C", "", "Commanded throttle actuator", Decoders.PERCENT),
    PID_4D("4D", "", "Time run with MIL on", Decoders.RUN_TIME_MIN),
    PID_4E("4E", "", "Time since trouble codes cleared", Decoders.RUN_TIME_MIN),
    PID_4F("4F", "", "Maximum value for equivalence ratio, oxygen sensor voltage, oxygen sensor current and intake manifold absolute pressure", Decoders.MAX_VALUES),
    PID_50("50", "", "Maximum value for mass air flow sensor", Decoders.AIR_FLOW_RATE_MAX),
    PID_51("51", "", "Fuel Type", Decoders.FUEL_TYPE),
    PID_52("52", "", "Ethanol Fuel Percent", Decoders.PERCENT),
    PID_53("53", "", "Absolute Evap system Vapor Pressure", Decoders.VAPOR_PRESSURE),
    PID_54("54", "", "Evap system vapor pressure", Decoders.EVAP_PRESSURE_ALT),
    PID_55("55", "", "Short term secondary O2 trim - Bank 1", Decoders.TWO_PERCENT_CENTERED),
    PID_56("56", "", "Long term secondary O2 trim - Bank 1", Decoders.TWO_PERCENT_CENTERED),
    PID_57("57", "", "Short term secondary O2 trim - Bank 2", Decoders.TWO_PERCENT_CENTERED),
    PID_58("58", "", "Long term secondary O2 trim - Bank 2", Decoders.TWO_PERCENT_CENTERED),
    PID_59("59", "", "Fuel rail pressure (absolute)", Decoders.RAIL_GAUGE_PRESSURE),
    PID_5A("5A", "", "Relative accelerator pedal position", Decoders.PERCENT),
    PID_5B("5B", "", "Hybrid battery pack remaining life", Decoders.PERCENT),
    PID_5C("5C", "", "Engine oil temperature", Decoders.TEMPERATURE),
    PID_5D("5D", "", "Fuel injection timing", Decoders.INJECT_TIMING),
    PID_5E("5E", "", "Engine fuel rate", Decoders.FUEL_RATE),
    PID_5F("5F", "", "Designed emission requirements", Decoders.DEFAULT),  // TODO UNKNOWN
    PID_60("60", "", "Supported PIDs [61-80]", Decoders.PIDS_61_80),

    ;

    companion object {
        private val map: Map<String, ObdPids> = entries.associateBy(ObdPids::pid)

        operator fun get(pid: String) = map[pid] ?: NO_PID_FOUND
        fun parse(rawData: String, timestamp: Long): DecodedPidValue
        {
            val data = rawData.replace("\\s".toRegex(), "")
            if (data.length > 10)
            {
                val ecu = data.slice(0..2)
                val answerLength = data.slice(3..4)
                val errorCode = data.slice(5..5)
                val mode = data.slice(6..6)
                val pidString = data.slice(7..8)
                val pidValue = data.slice(9..<data.length)
                if (ecu == "7E8" && errorCode == "4" && mode == "1")
                {
                    val pid = ObdPids[pidString]
                    return DecodedPidValue(timestamp, rawData, pid, pid.decoder.decode(pidValue))
                }
            }
            return DecodedPidValue(timestamp, rawData, NO_PID_FOUND, listOf(RawData.raw(rawData)))
        }
    }
}

fun getA(input: String) : Double { return input.slice(0..1).toUInt(radix = 16).toDouble() }
fun getB(input: String) : Double { return input.slice(2..3).toUInt(radix = 16).toDouble() }
fun getC(input: String) : Double { return input.slice(4..5).toUInt(radix = 16).toDouble() }
fun getD(input: String) : Double { return input.slice(6..7).toUInt(radix = 16).toDouble() }

fun getAB(input: String) : Double { return input.slice(0..3).toUInt(radix = 16).toDouble() }
fun getCD(input: String) : Double { return input.slice(4..7).toUInt(radix = 16).toDouble() }

fun getSignedAB(input: String) : Double { return input.slice(0..3).toInt(radix = 16).toDouble() }
fun getSignedCD(input: String) : Double { return input.slice(4..7).toInt(radix = 16).toDouble() }

fun getBitsFirstByte(input: String) : List<Boolean> { return List(8) { i -> ((input.slice(0..1).toULong(radix = 16) shr i) and 1uL) != 0uL }.reversed() }
fun getBitsFourBytes(input: String) : List<Boolean> { return List(32) { i -> ((input.slice(0..7).toULong(radix = 16) shr i) and 1uL) != 0uL }.reversed() }

enum class Decoders(val decode: (String) -> List<Value>) {
    DEFAULT({ input -> listOf(RawData.raw(input)) }),
    BOOLEAN({ input -> listOf(Bool.boolRaw(getBitsFirstByte(input)[7])) }),
    INTEGER({ input -> listOf(RawData.raw(getA(input).toString())) }),
    AIR_FUEL_RATIO({ input -> listOf(Ratio.ratio(getAB(input) / 32768)) }),
    PERCENT({ input -> listOf(Ratio.percents(getA(input) * 100 / 255)) }),
    PERCENT_CENTERED({ input -> listOf(Ratio.percents((getA(input) * 100 / 128) - 100)) }),
    ABSOLUTE_LOAD({ input -> listOf(Ratio.percents(getAB(input) * 100 / 255)) }),
    SPEED({ input -> listOf(Speed.kiloMetersPerHour(getA(input))) }),
    MODULE_VOLTAGE({ input -> listOf(Voltage.volts(getAB(input) / 1000)) }),
    TEMPERATURE({ input -> listOf(Temperature.celsius(getA(input) - 40)) }),
    SENSOR_TEMPERATURE({ input -> listOf(Temperature.celsius(getAB(input) / 10 - 40)) }),
    FUEL_PRESSURE({ input -> listOf(Pressure.kiloPascal(getA(input) * 3)) }),
    PRESSURE({ input -> listOf(Pressure.kiloPascal(getA(input))) }),
    RAIL_PRESSURE({ input -> listOf(Pressure.kiloPascal(getAB(input) * 0.079f)) }),
    RAIL_GAUGE_PRESSURE({ input -> listOf(Pressure.kiloPascal(getAB(input) * 10)) }),
    VAPOR_PRESSURE({ input -> listOf(Pressure.kiloPascal(getAB(input) / 200)) }),
    EVAP_PRESSURE({ input -> listOf(Pressure.pascal(getSignedAB(input) / 4)) }),
    EVAP_PRESSURE_ALT({ input -> listOf(Pressure.pascal(getSignedAB(input))) }),
    RPM({ input -> listOf(Frequency.revolutionsPerMinute(getAB(input) / 4)) }),
    TIMING_ADVANCE({ input -> listOf(Ratio.ratio(getA(input) / 2 - 64)) }),
    INJECT_TIMING({ input -> listOf(Ratio.ratio(getAB(input) / 128 - 210)) }),
    AIR_FLOW_RATE({ input -> listOf(MassFlow.gramsPerSecond(getAB(input) / 100)) }),
    AIR_FLOW_RATE_MAX({ input -> listOf(MassFlow.gramsPerSecond(getA(input) * 10)) }),
    FUEL_RATE({ input -> listOf(VolumeFlow.litersPerSecond(getAB(input) / 20)) }),
    RUN_TIME_SEC({ input -> listOf(Time.seconds(getAB(input))) }),
    RUN_TIME_MIN({ input -> listOf(Time.minutes(getAB(input))) }),
    DISTANCE({ input -> listOf(Distance.kiloMeters(getAB(input))) }),

    SENSOR_VOLTAGE({ input -> listOf(Voltage.volts(getA(input) / 200), Ratio.percents((getB(input) * 100 / 128) - 100)) }),
    OXYGEN_VOLTAGE({ input -> listOf(Ratio.ratio(getAB(input) / 32768), Voltage.volts(getCD(input) / 8192)) }),
    OXYGEN_SENSOR({ input -> listOf(Ratio.ratio(getAB(input) / 32768), ElectricCurrent.milliAmpere(getCD(input) / 256 - 128)) }),
    TWO_PERCENT_CENTERED({ input -> listOf(Ratio.percents((getA(input) * 100 / 128) - 100), Ratio.percents((getB(input) * 100 / 128) - 100)) }),

    MAX_VALUES({ input -> listOf(Ratio.ratio(getA(input)), Voltage.volts(getB(input)), ElectricCurrent.milliAmpere(getC(input)), Pressure.kiloPascal(getD(input) * 10)) }),
    MONITOR_STATUS({ input -> listOf(
        Bool.boolIndexed(getBitsFirstByte(input)[0], "MIL"), Ratio.ratio((getA(input).toULong() % 128uL).toDouble()),
        Bool.boolIndexed(getBitsFourBytes(input)[9], "Components completeness"), Bool.boolIndexed(getBitsFourBytes(input)[10], "Fuel System completeness"), Bool.boolIndexed(getBitsFourBytes(input)[11], "Misfire completeness"),
        RawData.raw(if (getBitsFourBytes(input)[12]) "Compression ignition" else "Spark ignition"),
        Bool.boolIndexed(getBitsFourBytes(input)[13], "Components availability"), Bool.boolIndexed(getBitsFourBytes(input)[14], "Fuel System availability"), Bool.boolIndexed(getBitsFourBytes(input)[15], "Misfire availability"),
                                              ) +
        getBitsFourBytes(input).slice(16..23).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 1)) } +
        getBitsFourBytes(input).slice(24..31).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 1)) }
                   }),
    DTC_TRIGGERED({ input -> listOf(Ratio.ratio(getA(input)), Voltage.volts(getB(input)), ElectricCurrent.milliAmpere(getC(input)), Pressure.kiloPascal(getD(input) * 10)) }),
    SENSORS_1_8({ input -> listOf(RawData.raw(if (!getBitsFirstByte(input)[0] && !getBitsFirstByte(input)[1]) "P "        // 00
                                                        else if (!getBitsFirstByte(input)[0] && getBitsFirstByte(input)[1]) "C "    // 01
                                                        else if (getBitsFirstByte(input)[0] && !getBitsFirstByte(input)[1]) "B "    // 10
                                                        else "U " +                                                                 // 11
                                                        Integer.toHexString((getA(input).toUInt() % 64u).toInt()) + ' ' + Integer.toHexString(getB(input).toInt()))) }),
    SENSORS_1_8_ALT({ input -> getBitsFirstByte(input).reversed().mapIndexed { index, it -> Bool.boolIndexed(it, (index + 1).toString()) } }),

    FUEL_TYPE({ input -> listOf(FuelType.fromULong(getA(input).toULong())) }),
    OBD_STANDARDS({ input -> listOf(OBDStandard.fromULong(getA(input).toULong())) }),
    FUEL_SYSTEM_STATUS({ input -> listOf(FuelSystemStatus.fromULong(getA(input).toULong()), FuelSystemStatus.fromULong(getB(input).toULong())) }),
    AIR_STATUS({ input -> listOf(AirStatus.fromULong(getA(input).toULong())) }),

    PIDS_01_20({ input -> getBitsFourBytes(input).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 1)) } }),
    PIDS_21_40({ input -> getBitsFourBytes(input).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 21)) } }),
    PIDS_41_60({ input -> getBitsFourBytes(input).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 41)) } }),
    PIDS_61_80({ input -> getBitsFourBytes(input).mapIndexed { index, it -> Bool.boolIndexed(it, Integer.toHexString(index + 61)) } }),
    ;
}
