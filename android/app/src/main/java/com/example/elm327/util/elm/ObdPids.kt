package com.example.elm327.util.elm

enum class ObdPids(
    val pid: String,
    val descriptionShort: String,
    val descriptionLong: String,
    val decoder: Decoders
) {
    NO_PID_FOUND("", "", "", Decoders.DEFAULT),
    PID_00("00", "", "Supported PIDs [01-20]", Decoders.PIDS_1_20),

    //    PID_01("01", "", "Status since DTCs cleared", Decoders.DEFAULT),  // TODO
//    PID_02("02", "", "DTC that triggered the freeze frame", Decoders.DEFAULT),  // TODO
//    PID_03("03", "", "Fuel System Status", Decoders.DEFAULT),  // TODO
//    PID_04("04", "", "Calculated Engine Load", Decoders.PERCENT),
//    PID_05("05", "", "Engine Coolant Temperature", Decoders.TEMPERATURE),
//    PID_06("06", "", "Short Term Fuel Trim - Bank 1", Decoders.PERCENT_CENTERED),
//    PID_07("07", "", "Long Term Fuel Trim - Bank 1", Decoders.PERCENT_CENTERED),
//    PID_08("08", "", "Short Term Fuel Trim - Bank 2", Decoders.PERCENT_CENTERED),
//    PID_09("09", "", "Long Term Fuel Trim - Bank 2", Decoders.PERCENT_CENTERED),
//    PID_0A("0A", "", "Fuel Pressure", Decoders.FUEL_PRESSURE),
//    PID_0B("0B", "", "Intake Manifold Pressure", Decoders.GET_A),
//    PID_0C("0C", "", "Engine RPM", Decoders.RPM),
    PID_0D("0D", "", "Vehicle Speed", Decoders.A),
//    PID_0E("0E", "", "Timing Advance", Decoders.TIMING_ADVANCE),
//    PID_0F("0F", "", "Intake Air Temp", Decoders.TEMPERATURE),
//    PID_10("10", "", "Air Flow Rate (MAF)", Decoders.AIR_FLOW_RATE),
//    PID_11("11", "", "Throttle Position", Decoders.PERCENT),
//    PID_12("12", "", "Secondary Air Status", Decoders.DEFAULT),  // TODO
//    PID_13("13", "", "O2 Sensors Present", Decoders.GET_4BIT_BANKS),
//    PID_14("14", "", "O2: Bank 1 - Sensor 1 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_15("15", "", "O2: Bank 1 - Sensor 2 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_16("16", "", "O2: Bank 1 - Sensor 3 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_17("17", "", "O2: Bank 1 - Sensor 4 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_18("18", "", "O2: Bank 2 - Sensor 1 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_19("19", "", "O2: Bank 2 - Sensor 2 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_1A("1A", "", "O2: Bank 2 - Sensor 3 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_1B("1B", "", "O2: Bank 2 - Sensor 4 Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_1C("1C", "", "OBD Standards Compliance", Decoders.DEFAULT),  // TODO
//    PID_1D("1D", "", "O2 Sensors Present (alternate)", Decoders.GET_2BIT_BANKS),
//    PID_1E("1E", "", "Auxiliary input status (power take off)", Decoders.GET_FIRST_BIT),
//    PID_1F("1F", "", "Engine Run Time", Decoders.GET_AB),
//    PID_20("20", "", "Supported PIDs [21-40]", Decoders.PID_GETTER),
//    PID_21("21", "", "Distance Traveled with MIL on", Decoders.GET_AB),
//    PID_22("22", "", "Fuel Rail Pressure (relative to vacuum)", Decoders.PRESSURE_RELATIVE_TO_MANIFOLD),
//    PID_23("23", "", "Fuel Rail Pressure (direct inject)", Decoders.PRESSURE_DIESEL),
//    PID_24("24", "", "02 Sensor 1 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),  // TODO : ((A*256)+B)*2/65535 ????or????? ((A*256)+B)/32768
//    PID_25("25", "", "02 Sensor 2 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_26("26", "", "02 Sensor 3 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_27("27", "", "02 Sensor 4 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_28("28", "", "02 Sensor 5 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_29("29", "", "02 Sensor 6 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_2A("2A", "", "02 Sensor 7 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_2B("2B", "", "02 Sensor 8 WR Lambda Voltage", Decoders.SENSOR_VOLTAGE),
//    PID_2C("2C", "", "Commanded EGR", Decoders.PERCENT),
//    PID_2D("2D", "", "EGR Error", Decoders.PERCENT_CENTERED),
//    PID_2E("2E", "", "Commanded Evaporative Purge", Decoders.PERCENT),
//    PID_2F("2F", "", "Fuel Level Input", Decoders.PERCENT),
//    PID_30("30", "", "Number of warm-ups since codes cleared", Decoders.GET_A),
//    PID_31("31", "", "Distance traveled since codes cleared", Decoders.GET_AB),
//    PID_32("32", "", "Evaporative system vapor pressure", Decoders.EVAP_PRESSURE),
//    PID_33("33", "", "Barometric Pressure", Decoders.GET_A),
//    PID_34("34", "", "02 Sensor 1 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_35("35", "", "02 Sensor 2 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_36("36", "", "02 Sensor 3 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_37("37", "", "02 Sensor 4 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_38("38", "", "02 Sensor 5 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_39("39", "", "02 Sensor 6 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_3A("3A", "", "02 Sensor 7 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_3B("3B", "", "02 Sensor 8 WR Lambda Current", Decoders.SENSOR_PRESSURE),
//    PID_3C("3C", "", "Catalyst Temperature: Bank 1 - Sensor 1", Decoders.SENSOR_TEMPERATURE),
//    PID_3D("3D", "", "Catalyst Temperature: Bank 2 - Sensor 1", Decoders.SENSOR_TEMPERATURE),
//    PID_3E("3E", "", "Catalyst Temperature: Bank 1 - Sensor 2", Decoders.SENSOR_TEMPERATURE),
//    PID_3F("3F", "", "Catalyst Temperature: Bank 2 - Sensor 2", Decoders.SENSOR_TEMPERATURE),
//    PID_40("40", "", "Supported PIDs [41-60]", Decoders.PID_GETTER),
//    PID_41("41", "", "Monitor status this drive cycle", Decoders.GET_BITS),
//    PID_42("42", "", "Control module voltage", Decoders.MODULE_VOLTAGE),
//    PID_43("43", "", "Absolute load value", Decoders.ABSOLUTE_LOAD),
//    PID_44("44", "", "Commanded equivalence ratio", Decoders.AIR_FUEL_RATIO),
//    PID_45("45", "", "Relative throttle position", Decoders.PERCENT),
//    PID_46("46", "", "Ambient air temperature", Decoders.TEMPERATURE),
//    PID_47("47", "", "Absolute throttle position B", Decoders.PERCENT),
//    PID_48("48", "", "Absolute throttle position C", Decoders.PERCENT),
//    PID_49("49", "", "Accelerator pedal position D", Decoders.PERCENT),
//    PID_4A("4A", "", "Accelerator pedal position E", Decoders.PERCENT),
//    PID_4B("4B", "", "Accelerator pedal position F", Decoders.PERCENT),
//    PID_4C("4C", "", "Commanded throttle actuator", Decoders.PERCENT),
//    PID_4D("4D", "", "Time run with MIL on", Decoders.GET_AB),
//    PID_4E("4E", "", "Time since trouble codes cleared", Decoders.GET_AB),
//    PID_4F("4F", "", "Maximum value for equivalence ratio, oxygen sensor voltage, oxygen sensor current and intake manifold absolute pressure", Decoders.MAX_VALUES),
//    PID_50("50", "", "Maximum value for mass air flow sensor", Decoders.MAX_MAF),
//    PID_51("51", "", "Fuel Type", Decoders.DEFAULT),  // TODO
//    PID_52("52", "", "Ethanol Fuel Percent", Decoders.PERCENT),
//    PID_53("53", "", "Absolute Evap system Vapor Pressure", Decoders.ABS_EVAP_PRESSURE),
//    PID_54("54", "", "Evap system vapor pressure", Decoders.EVAP_PRESSURE_ALT),
//    PID_55("55", "", "Short term secondary O2 trim - Bank 1", Decoders.TWO_PERCENT_CENTERED),
//    PID_56("56", "", "Long term secondary O2 trim - Bank 1", Decoders.TWO_PERCENT_CENTERED),
//    PID_57("57", "", "Short term secondary O2 trim - Bank 2", Decoders.TWO_PERCENT_CENTERED),
//    PID_58("58", "", "Long term secondary O2 trim - Bank 2", Decoders.TWO_PERCENT_CENTERED),
//    PID_59("59", "", "Fuel rail pressure (absolute)", Decoders.FUEL_RAIL_PRESSURE),
//    PID_5A("5A", "", "Relative accelerator pedal position", Decoders.PERCENT),
//    PID_5B("5B", "", "Hybrid battery pack remaining life", Decoders.PERCENT),
//    PID_5C("5C", "", "Engine oil temperature", Decoders.TEMPERATURE),
//    PID_5D("5D", "", "Fuel injection timing", Decoders.INJECT_TIMING),
//    PID_5E("5E", "", "Engine fuel rate", Decoders.FUEL_RATE),
//    PID_5F("5F", "", "Designed emission requirements", Decoders.DEFAULT),  // TODO

    ;

    companion object {
        private val map: Map<String, ObdPids> = entries.associateBy(ObdPids::pid)
        operator fun get(pid: String) = map[pid] ?: NO_PID_FOUND

        fun parse(rawData: String): Pair<ObdPids, DecodedValue> {
            val data = rawData.replace("\\s".toRegex(), "")
            if (data.length == 19) {
                val ecu = data.slice(0..2)
                val answerLength = data.slice(3..4)
                val errorCode = data.slice(5..5)
                val mode = data.slice(6..6)
                val pidString = data.slice(7..8)
                val pidValue = data.slice(9..<data.length)
                if (ecu == "7E8" && errorCode == "4" && mode == "1") {
                    val pid = ObdPids[pidString]
                    return Pair(pid, pid.decoder.decode(pidValue))
                }
            }
            return Pair(NO_PID_FOUND, RawData(rawData))
        }
    }
}


enum class Decoders(val decode: (String) -> DecodedValue) {

    DEFAULT({ value -> RawData(value) }),
    A({ value -> SingleValue(Parser(value).A, Printers.SPEED) }),
    PIDS_1_20({ value -> PidsList(Parser(value).bits, 1, Printers.PIDS_1_20) }),
    PERCENT({ value -> SingleValue(Parser(value).A * 100 / 255, Printers.PERCENTAGE) }),
    PERCENT_CENTERED({ value ->
        SingleValue(
            (Parser(value).A - 128) * 100 / 128,
            Printers.PERCENTAGE
        )
    }),
    TEMPERATURE({ value -> SingleValue(Parser(value).A + 233, Printers.TEMPERATURE) }),
    FUEL_PRESSURE({ value -> SingleValue(Parser(value).A * 3, Printers.TEMPERATURE) }),
    RPM({ value -> SingleValue(Parser(value).AB / 4, Printers.ENGINE_SPEED) }),
    TIMING_ADVANCE({ value -> SingleValue(Parser(value).A / 2 - 64, Printers.ANGLE) }),
    AIR_FLOW_RATE({ value -> SingleValue(Parser(value).AB / 100, Printers.FLOW) }),

    ;


    class Parser(private var rawData: String) {
        val A = rawData.slice(0..1).toInt(radix = 16).toFloat()
        val B = rawData.slice(2..3).toInt(radix = 16).toFloat()
        val C = rawData.slice(4..5).toInt(radix = 16).toFloat()
        val D = rawData.slice(6..7).toInt(radix = 16).toFloat()

        val AB = rawData.slice(0..3).toInt(radix = 16).toFloat()
        val CD = rawData.slice(4..7).toInt(radix = 16).toFloat()

        val num = rawData.slice(0..7).toLong(16)
        val bits = List(32) { i -> ((num shr i) and 1).toInt() }.reversed()
    }
}

abstract class DecodedValue(private val printer: Printers) {
    override fun toString(): String {
        return printer.print(this)
    }
}

class RawData(val rawData: String) : DecodedValue(Printers.DEFAULT)
class SingleValue(val singleValue: Float, printer: Printers) : DecodedValue(printer)
class TwoValues(val first: Float, val second: Float, printer: Printers) : DecodedValue(printer)
class PidsList(val pids: List<Int>, firstPidNumber: Int, printer: Printers) :
    DecodedValue(printer) {
    val hexList = List(32) {
        if (pids[it] == 1) (it + firstPidNumber).toString(16).padStart(2, '0').uppercase() else "0"
    }.filter { it != "0" }
    val pidsList = hexList.map { ObdPids[it] }
}

enum class Printers(val print: (DecodedValue) -> String) {
    DEFAULT({ value -> (value as RawData).rawData }),
    PERCENTAGE({ value -> "${(value as SingleValue).singleValue} %" }),
    VOLTAGE({ value -> "${(value as SingleValue).singleValue} V" }),
    TEMPERATURE({ value -> "${(value as SingleValue).singleValue} K" }),
    PRESSURE_KPA({ value -> "${(value as SingleValue).singleValue} kPa" }),
    PRESSURE_PA({ value -> "${(value as SingleValue).singleValue} Pa" }),
    ENGINE_SPEED({ value -> "${(value as SingleValue).singleValue} rpm" }),
    SPEED({ value -> "${(value as SingleValue).singleValue} km/h" }),
    ANGLE({ value -> "${(value as SingleValue).singleValue} °" }),
    FLOW({ value -> "${(value as SingleValue).singleValue} g/s" }),
    VOLTAGE_AND_PERCENTAGE({ value -> "${(value as TwoValues).first} V, ${value.second} %" }),
    TIME({ value -> "${(value as SingleValue).singleValue} sec" }),
    DISTANCE({ value -> "${(value as SingleValue).singleValue} km" }),
    RATIO_AND_VOLTAGE({ value -> "${(value as TwoValues).first} (ratio), ${value.second} V" }),
    PIDS_1_20({ value -> "supported PIDs (1 - 20): ${
        (value as PidsList).hexList
    }"})
}