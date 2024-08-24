package com.example.elm327.util.elm

import android.util.Log


enum class FuelType(val num: ULong, val descrtiption: String) {
    NOT_AVAILABLE(0u, "Not available"),
    GASOLINE(1u, "Gasoline"),
    METHANOL(2u, "Methanol"),
    ETHANOL(3u, "Ethanol"),
    DIESEL(4u, "Diesel"),
    LPG(5u, "LPG"),
    CNG(6u, "CNG"),
    PROPANE(7u, "Propane"),
    ELECTRIC(8u, "Electric"),
    BIFUEL_RUNNING_GASOLINE(9u, "Bifuel running Gasoline"),
    BIFUEL_RUNNING_METHANOL(10u, "Bifuel running Methanol"),
    BIFUEL_RUNNING_ETHANOL(11u, "Bifuel running Ethanol"),
    BIFUEL_RUNNING_LPG(12u, "Bifuel running LPG"),
    BIFUEL_RUNNING_CNG(13u, "Bifuel running CNG"),
    BIFUEL_RUNNING_PROPANE(14u, "Bifuel running Propane"),
    BIFUEL_RUNNING_ELECTRICITY(15u, "Bifuel running Electricity"),
    BIFUEL_RUNNING_ELECTRIC_AND_COMBUSTION_ENGINE(16u, "Bifuel running electric and combustion engine"),
    HYBRID_GASOLINE(17u, "Hybrid gasoline"),
    HYBRID_ETHANOL(18u, "Hybrid Ethanol"),
    HYBRID_DIESEL(19u, "Hybrid Diesel"),
    HYBRID_ELECTRIC(20u, "Hybrid Electric"),
    HYBRID_RUNNING_ELECTRIC_AND_COMBUSTION_ENGINE(21u, "Hybrid running electric and combustion engine"),
    HYBRID_REGENERATIVE(22u, "Hybrid Regenerative"),
    BIFUEL_RUNNING_DIESEL(23u, "Bifuel running diesel"),

    FUEL_TYPE_UNKNOWN(24u, "Unknown Fuel Type"),
    ;

    companion object{
        private val map: Map<ULong, FuelType> = FuelType.entries.associateBy(FuelType::num)
        operator fun get(num: ULong) = map[num] ?: FUEL_TYPE_UNKNOWN
    }
}

enum class OBDStandards(val num: ULong, val descrtiption: String) {
    OBD_II_AS_DEFINED_BY_THE_CARB(1u, "OBD-II as defined by the CARB"),
    OBD_AS_DEFINED_BY_THE_EPA(2u, "OBD as defined by the EPA"),
    OBD_AND_OBD_II(3u, "OBD and OBD-II"),
    OBD_I(4u, "OBD-I"),
    NOT_OBD_COMPLIANT(5u, "Not OBD compliant"),
    EOBD_EUROPE(6u, "EOBD (Europe)"),
    EOBD_AND_OBD_II(7u, "EOBD and OBD-II"),
    EOBD_AND_OBD(8u, "EOBD and OBD"),
    EOBD_OBD_AND_OBD_II(9u, "EOBD, OBD and OBD II"),
    JOBD_JAPAN(10u, "JOBD (Japan)"),
    JOBD_AND_OBD_II(11u, "JOBD and OBD II"),
    JOBD_AND_EOBD(12u, "JOBD and EOBD"),
    JOBD_EOBD_AND_OBD_II(13u, "JOBD, EOBD, and OBD II"),
    ENGINE_MANUFACTURER_DIAGNOSTICS_EMD(17u, "Engine Manufacturer Diagnostics (EMD)"),
    ENGINE_MANUFACTURER_DIAGNOSTICS_ENHANCED_EMD(18u, "Engine Manufacturer Diagnostics Enhanced (EMD+)"),
    HEAVY_DUTY_ON_BOARD_DIAGNOSTICS_CHILD_PARTIAL_HD_OBD_C(19u, "Heavy Duty On-Board Diagnostics (Child/Partial) (HD OBD-C)"),
    HEAVY_DUTY_ON_BOARD_DIAGNOSTICS_HD_OBD(20u, "Heavy Duty On-Board Diagnostics (HD OBD)"),
    WORLD_WIDE_HARMONIZED_OBD_WWH_OBD(21u, "World Wide Harmonized OBD (WWH OBD)"),
    HEAVY_DUTY_EURO_OBD_STAGE_I_WITHOUT_NOX_CONTROL_HD_EOBD_I(23u, "Heavy Duty Euro OBD Stage I without NOx control (HD EOBD-I)"),
    HEAVY_DUTY_EURO_OBD_STAGE_I_WITH_NOX_CONTROL_HD_EOBD_I_N(24u, "Heavy Duty Euro OBD Stage I with NOx control (HD EOBD-I N)"),
    HEAVY_DUTY_EURO_OBD_STAGE_II_WITHOUT_NOX_CONTROL_HD_EOBD_II(25u, "Heavy Duty Euro OBD Stage II without NOx control (HD EOBD-II)"),
    HEAVY_DUTY_EURO_OBD_STAGE_II_WITH_NOX_CONTROL_HD_EOBD_II_N(26u, "Heavy Duty Euro OBD Stage II with NOx control (HD EOBD-II N)"),
    BRAZIL_OBD_PHASE_1_OBDBR_1(28u, "Brazil OBD Phase 1 (OBDBr-1)"),
    BRAZIL_OBD_PHASE_2_OBDBR_2(29u, "Brazil OBD Phase 2 (OBDBr-2)"),
    KOREAN_OBD_KOBD(30u, "Korean OBD (KOBD)"),
    INDIA_OBD_I_IOBD_I(31u, "India OBD I (IOBD I)"),
    INDIA_OBD_II_IOBD_II(32u, "India OBD II (IOBD II)"),
    HEAVY_DUTY_EURO_OBD_STAGE_VI_HD_EOBD_IV(33u, "Heavy Duty Euro OBD Stage VI (HD EOBD-IV)"),

    OBD_STANDARD_UNKNOWN(0u, "Unknown OBD Standard"),
    ;

    companion object{
        private val map: Map<ULong, OBDStandards> = OBDStandards.entries.associateBy(OBDStandards::num)
        operator fun get(num: ULong) = map[num] ?: OBD_STANDARD_UNKNOWN
    }
}

enum class FuelSystemStatus(val num: ULong, val descrtiption: String) {
    THE_MOTOR_IS_OFF(0u, "The motor is off"),
    OPEN_LOOP_DUE_TO_INSUFFICIENT_ENGINE_TEMPERATURE(1u, "Open loop due to insufficient engine temperature"),
    CLOSED_LOOP_USING_OXYGEN_SENSOR_FEEDBACK_TO_DETERMINE_FUEL_MIX(2u, "Closed loop, using oxygen sensor feedback to determine fuel mix"),
    OPEN_LOOP_DUE_TO_ENGINE_LOAD_OR_FUEL_CUT_DUE_TO_DECELERATION(4u, "Open loop due to engine load OR fuel cut due to deceleration"),
    OPEN_LOOP_DUE_TO_SYSTEM_FAILURE(8u, "Open loop due to system failure"),
    CLOSED_LOOP_USING_AT_LEAST_ONE_OXYGEN_SENSOR_BUT_THERE_IS_A_FAULT_IN_THE_FEEDBACK_SYSTEM(16u, "Closed loop, using at least one oxygen sensor but there is a fault in the feedback system"),

    SYSTEM_STATUS_UNKNOWN(3u, "Unknown Fuel System Status"),
    ;

    companion object{
        private val map: Map<ULong, FuelSystemStatus> = FuelSystemStatus.entries.associateBy(FuelSystemStatus::num)
        operator fun get(num: ULong) = map[num] ?: SYSTEM_STATUS_UNKNOWN
    }
}

enum class AirStatus(val num: ULong, val descrtiption: String)  {
    UPSTREAM(1u, "Upstream"),
    DOWNSTREAM_OF_CATALYTIC_CONVERTER(2u, "Downstream of catalytic converter"),
    FROM_THE_OUTSIDE_ATMOSPHERE_OR_OFF(4u, "From the outside atmosphere or off"),
    PUMP_COMMANDED_ON_FOR_DIAGNOSTIC(8u, "Pump commanded on for diagnostic"),

    AIR_STATUS_UNKNOWN(0u, "Unknown Air Status"),
    ;

    companion object{
        private val map: Map<ULong, AirStatus> = AirStatus.entries.associateBy(AirStatus::num)
        operator fun get(num: ULong) = map[num] ?: AIR_STATUS_UNKNOWN
    }
}

enum class ObdPids(val pid: String, val descriptionShort: String, val descriptionLong: String, val decoder: Decoders) {
    NO_PID_FOUND("", "", "", Decoders.DEFAULT),
    PID_00("00", "", "Supported PIDs [01-20]", Decoders.PIDS_01_20),

    PID_01("01", "", "Status since DTCs cleared", Decoders.DEFAULT),  // TODO AS PID 41
    PID_02("02", "", "DTC that triggered the freeze frame", Decoders.DEFAULT),  // TODO AS SERVICE 03
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
    PID_41("41", "", "Monitor status this drive cycle", Decoders.DEFAULT), // TODO AS PID 01
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

        fun parse(rawData: String): Pair<ObdPids, DecodedValue> {
            val data = rawData.replace("\\s".toRegex(), "")
            if (data.length > 10) {
                val ecu = data.slice(0..2)
                val answerLength = data.slice(3..4)
                val errorCode = data.slice(5..5)
                val mode = data.slice(6..6)
                val pidString = data.slice(7..8)
                val pidValue = data.slice(9..<data.length)
                Log.i("OUR", ecu + ';' + answerLength + ';' + errorCode + ';' + mode + ';' + pidString + ';' + pidValue)
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
    BOOLEAN({ value -> SingleValue(Parser(value).bits[7].toDouble(), Printers.BOOLEAN) }),
    INTEGER({ value -> SingleValue(Parser(value).A, Printers.PLAIN) }),
    AIR_FUEL_RATIO({ value -> SingleValue(Parser(value).AB / 32768, Printers.PLAIN) }),
    PERCENT({ value -> SingleValue(Parser(value).A * 100 / 255, Printers.PERCENTAGE) }),
    PERCENT_CENTERED({ value -> SingleValue((Parser(value).A * 100 / 128) - 100, Printers.PERCENTAGE) }),
    ABSOLUTE_LOAD({ value -> SingleValue(Parser(value).AB * 100 / 255, Printers.PERCENTAGE) }),
    SPEED({ value -> SingleValue(Parser(value).A, Printers.SPEED) }),
    MODULE_VOLTAGE({ value -> SingleValue(Parser(value).AB / 1000, Printers.VOLTAGE) }),
    TEMPERATURE({ value -> SingleValue(Parser(value).A + 233, Printers.TEMPERATURE) }),
    SENSOR_TEMPERATURE({ value -> SingleValue(Parser(value).AB / 10 + 233, Printers.TEMPERATURE) }),
    FUEL_PRESSURE({ value -> SingleValue(Parser(value).A * 3, Printers.PRESSURE_KPA) }),
    PRESSURE({ value -> SingleValue(Parser(value).A, Printers.PRESSURE_KPA) }),
    RAIL_PRESSURE({ value -> SingleValue(Parser(value).AB * 0.079f, Printers.PRESSURE_KPA) }),
    RAIL_GAUGE_PRESSURE({ value -> SingleValue(Parser(value).AB * 10, Printers.PRESSURE_KPA) }),
    VAPOR_PRESSURE({ value -> SingleValue(Parser(value).AB / 200, Printers.PRESSURE_KPA) }),
    EVAP_PRESSURE({ value -> SingleValue(Parser(value).AB_SIGNED / 4, Printers.PRESSURE_PA) }),
    EVAP_PRESSURE_ALT({ value -> SingleValue(Parser(value).AB_SIGNED, Printers.PRESSURE_PA) }),
    RPM({ value -> SingleValue(Parser(value).AB / 4, Printers.ENGINE_SPEED) }),
    TIMING_ADVANCE({ value -> SingleValue(Parser(value).A / 2 - 64, Printers.ANGLE) }),
    INJECT_TIMING({ value -> SingleValue(Parser(value).AB / 128 - 210, Printers.ANGLE) }),
    AIR_FLOW_RATE({ value -> SingleValue(Parser(value).AB / 100, Printers.FLOW_GRAMS) }),
    AIR_FLOW_RATE_MAX({ value -> SingleValue(Parser(value).A * 10, Printers.FLOW_GRAMS) }),
    FUEL_RATE({ value -> SingleValue(Parser(value).AB / 20, Printers.FLOW_LITERS) }),
    RUN_TIME_SEC({ value -> SingleValue(Parser(value).AB, Printers.TIME) }),
    RUN_TIME_MIN({ value -> SingleValue(Parser(value).AB * 60, Printers.TIME) }),
    DISTANCE({ value -> SingleValue(Parser(value).AB, Printers.DISTANCE) }),

    SENSOR_VOLTAGE({ value -> TwoValues(Parser(value).A / 200, (Parser(value).B * 100 / 128) - 100, Printers.VOLTAGE_AND_PERCENTAGE) }),
    OXYGEN_VOLTAGE({ value -> TwoValues(Parser(value).AB / 32768, Parser(value).CD / 8192, Printers.RATIO_AND_VOLTAGE) }),
    OXYGEN_SENSOR({ value -> TwoValues(Parser(value).AB / 32768, Parser(value).CD / 256 - 128, Printers.RATIO_AND_MILLIAMPERE) }),
    TWO_PERCENT_CENTERED({ value -> TwoValues((Parser(value).A * 100 / 128) - 100, (Parser(value).B * 100 / 128) - 100, Printers.TWO_PERCENTS) }),

    MAX_VALUES({ value -> FourValues(Parser(value).A, Parser(value).B, Parser(value).C, Parser(value).D * 10, Printers.MAX_VALUES) }),

    SENSORS_1_8({ value -> EightValues(Parser(value).bits[7].toDouble(), Parser(value).bits[6].toDouble(), Parser(value).bits[5].toDouble(), Parser(value).bits[4].toDouble(),
                                                 Parser(value).bits[3].toDouble(), Parser(value).bits[2].toDouble(), Parser(value).bits[1].toDouble(), Parser(value).bits[0].toDouble(), Printers.SENSORS_1_8)
    }),
    SENSORS_1_8_ALT({ value -> EightValues(Parser(value).bits[7].toDouble(), Parser(value).bits[6].toDouble(), Parser(value).bits[5].toDouble(), Parser(value).bits[4].toDouble(),
                                                     Parser(value).bits[3].toDouble(), Parser(value).bits[2].toDouble(), Parser(value).bits[1].toDouble(), Parser(value).bits[0].toDouble(), Printers.SENSORS_1_8)
    }),

    FUEL_TYPE({ value -> FuelValue(FuelType[Parser(value).A.toULong()], Printers.FUEL_TYPE) }),
    OBD_STANDARDS({ value -> OBDStandardValue(OBDStandards[Parser(value).A.toULong()], Printers.OBD_STANDARDS) }),
    FUEL_SYSTEM_STATUS({ value -> FuelSystemStatusValue(FuelSystemStatus[Parser(value).A.toULong()], FuelSystemStatus[Parser(value).B.toULong()], Printers.FUEL_SYSTEM_STATUS) }),
    AIR_STATUS({ value -> AirStatusValue(AirStatus[Parser(value).A.toULong()], Printers.AIR_STATUS) }),

    PIDS_01_20({ value -> PidsList(Parser(value).bits, 1, Printers.PIDS_01_20) }),
    PIDS_21_40({ value -> PidsList(Parser(value).bits, 21, Printers.PIDS_21_40) }),
    PIDS_41_60({ value -> PidsList(Parser(value).bits, 41, Printers.PIDS_41_60) }),
    PIDS_61_80({ value -> PidsList(Parser(value).bits, 61, Printers.PIDS_61_80) }),
    ;


    class Parser(private var rawData: String) {
        val A = rawData.slice(0..1).toUInt(radix = 16).toDouble()
        val B = rawData.slice(2..3).toUInt(radix = 16).toDouble()
        val C = 1.0//rawData.slice(4..5).toUInt(radix = 16).toDouble()
        val D = 1.0//rawData.slice(6..7).toUInt(radix = 16).toDouble()

        val AB = rawData.slice(0..3).toUInt(radix = 16).toDouble()
        val CD = 1.0//rawData.slice(4..7).toUInt(radix = 16).toDouble()

        val AB_SIGNED = 1.0//rawData.slice(0..3).toInt(radix = 16).toDouble()

        val num = 1//rawData.slice(0..7).toULong(16)
        val bits = listOf<UInt>()//List(32) { i -> ((num shr i) and 1u).toUInt() }.reversed()
    }
}

abstract class DecodedValue(private val printer: Printers) {
    override fun toString(): String {
        return printer.print(this)
    }
}

class RawData(val rawData: String) : DecodedValue(Printers.DEFAULT)
class FuelValue(val fuelType: FuelType, printer: Printers) : DecodedValue(printer)
class OBDStandardValue(val standard: OBDStandards, printer: Printers) : DecodedValue(printer)
class FuelSystemStatusValue(val fuelSystemStatus1: FuelSystemStatus, val fuelSystemStatus2: FuelSystemStatus, printer: Printers) : DecodedValue(printer)
class AirStatusValue(val airStatus: AirStatus, printer: Printers) : DecodedValue(printer)
class SingleValue(val singleValue: Double, printer: Printers) : DecodedValue(printer)
class TwoValues(val first: Double, val second: Double, printer: Printers) : DecodedValue(printer)
class FourValues(val first: Double, val second: Double, val third: Double, val fourth: Double, printer: Printers) : DecodedValue(printer)
class EightValues(val first: Double, val second: Double, val third: Double, val fourth: Double,
                  val fifth: Double, val sixth: Double, val seventh: Double, val eighth: Double, printer: Printers
                 ) : DecodedValue(printer)
class PidsList(val pids: List<UInt>, firstPidNumber: Int, printer: Printers) :
    DecodedValue(printer) {
    val hexList = List(32) {
        if (pids[it] == 1u) (it + firstPidNumber).toString(16).padStart(2, '0').uppercase() else "0"
    }.filter { it != "0" }
    val pidsList = hexList.map { ObdPids[it] }
}

enum class Printers(val print: (DecodedValue) -> String) {
    DEFAULT({ value -> (value as RawData).rawData }),
    BOOLEAN({ value -> if ((value as SingleValue).singleValue == 0.0) "Off" else "On" }),
    PLAIN({ value -> "${(value as SingleValue).singleValue}" }),
    PERCENTAGE({ value -> "${(value as SingleValue).singleValue} %" }),
    VOLTAGE({ value -> "${(value as SingleValue).singleValue} V" }),
    TEMPERATURE({ value -> "${(value as SingleValue).singleValue} K" }),
    PRESSURE_KPA({ value -> "${(value as SingleValue).singleValue} kPa" }),
    PRESSURE_PA({ value -> "${(value as SingleValue).singleValue} Pa" }),
    ENGINE_SPEED({ value -> "${(value as SingleValue).singleValue} rpm" }),
    SPEED({ value -> "${(value as SingleValue).singleValue} km/h" }),
    ANGLE({ value -> "${(value as SingleValue).singleValue} °" }),
    FLOW_GRAMS({ value -> "${(value as SingleValue).singleValue} g/s" }),
    FLOW_LITERS({ value -> "${(value as SingleValue).singleValue} L/s" }),
    VOLTAGE_AND_PERCENTAGE({ value -> "${(value as TwoValues).first} V, ${if (value.second > 99) "N/A" else value.second} %" }),  // This implements "if B==$FF, sensor is not used in trim calculation" condition
    TIME({ value -> "${(value as SingleValue).singleValue} sec" }),
    DISTANCE({ value -> "${(value as SingleValue).singleValue} km" }),

    RATIO_AND_VOLTAGE({ value -> "${(value as TwoValues).first} (ratio), ${value.second} V" }),
    RATIO_AND_MILLIAMPERE({ value -> "${(value as TwoValues).first} (ratio), ${value.second} mA" }),
    TWO_PERCENTS({ value -> "${(value as TwoValues).first} %, ${value.second} %" }),

    MAX_VALUES({ value -> "${(value as FourValues).first}, ${value.second} V, ${value.third} mA, ${value.fourth} kPa" }),

    SENSORS_1_8({ value -> "First: ${if ((value as EightValues).first == 0.0) "Off" else "On"}; Second: ${if (value.second == 0.0) "Off" else "On"}; Third: ${if (value.third == 0.0) "Off" else "On"}; Fourth: ${if (value.fourth == 0.0) "Off" else "On"}" +
                                         "; Fifth: ${if (value.fifth == 0.0) "Off" else "On"}; Sixth: ${if (value.sixth == 0.0) "Off" else "On"}; Seventh: ${if (value.seventh == 0.0) "Off" else "On"}; Eighth: ${if (value.eighth == 0.0) "Off" else "On"}" }),

    FUEL_TYPE({ value -> (value as FuelValue).fuelType.descrtiption }),
    OBD_STANDARDS({ value -> (value as OBDStandardValue).standard.descrtiption }),
    FUEL_SYSTEM_STATUS({ value -> "First: ${(value as FuelSystemStatusValue).fuelSystemStatus1.descrtiption}; Second: ${value.fuelSystemStatus2.descrtiption}" }),
    AIR_STATUS({ value -> (value as AirStatusValue).airStatus.descrtiption }),

    PIDS_01_20({ value -> "supported PIDs (1 - 20): ${(value as PidsList).hexList}"}),
    PIDS_21_40({ value -> "supported PIDs (21 - 40): ${(value as PidsList).hexList}"}),
    PIDS_41_60({ value -> "supported PIDs (41 - 60): ${(value as PidsList).hexList}"}),
    PIDS_61_80({ value -> "supported PIDs (61 - 80): ${(value as PidsList).hexList}"}),
}