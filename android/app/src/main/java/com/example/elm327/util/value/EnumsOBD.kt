package com.example.elm327.util.value

enum class FuelTypes(val num: ULong, val description: String)
{
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

    companion object
    {
        private val map: Map<ULong, FuelTypes> = FuelTypes.entries.associateBy(FuelTypes::num)
        operator fun get(num: ULong) = map[num] ?: FUEL_TYPE_UNKNOWN
    }
}

enum class OBDStandards(val num: ULong, val description: String)
{
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

    companion object
    {
        private val map: Map<ULong, OBDStandards> = OBDStandards.entries.associateBy(OBDStandards::num)
        operator fun get(num: ULong) = map[num] ?: OBD_STANDARD_UNKNOWN
    }
}

enum class FuelSystemStatuses(val num: ULong, val description: String)
{
    THE_MOTOR_IS_OFF(0u, "The motor is off"),
    OPEN_LOOP_DUE_TO_INSUFFICIENT_ENGINE_TEMPERATURE(1u, "Open loop due to insufficient engine temperature"),
    CLOSED_LOOP_USING_OXYGEN_SENSOR_FEEDBACK_TO_DETERMINE_FUEL_MIX(2u, "Closed loop, using oxygen sensor feedback to determine fuel mix"),
    OPEN_LOOP_DUE_TO_ENGINE_LOAD_OR_FUEL_CUT_DUE_TO_DECELERATION(4u, "Open loop due to engine load OR fuel cut due to deceleration"),
    OPEN_LOOP_DUE_TO_SYSTEM_FAILURE(8u, "Open loop due to system failure"),
    CLOSED_LOOP_USING_AT_LEAST_ONE_OXYGEN_SENSOR_BUT_THERE_IS_A_FAULT_IN_THE_FEEDBACK_SYSTEM(16u, "Closed loop, using at least one oxygen sensor but there is a fault in the feedback system"),

    SYSTEM_STATUS_UNKNOWN(3u, "Unknown Fuel System Status"),
    ;

    companion object
    {
        private val map: Map<ULong, FuelSystemStatuses> = FuelSystemStatuses.entries.associateBy(FuelSystemStatuses::num)
        operator fun get(num: ULong) = map[num] ?: SYSTEM_STATUS_UNKNOWN
    }
}

enum class AirStatuses(val num: ULong, val description: String)
{
    UPSTREAM(1u, "Upstream"),
    DOWNSTREAM_OF_CATALYTIC_CONVERTER(2u, "Downstream of catalytic converter"),
    FROM_THE_OUTSIDE_ATMOSPHERE_OR_OFF(4u, "From the outside atmosphere or off"),
    PUMP_COMMANDED_ON_FOR_DIAGNOSTIC(8u, "Pump commanded on for diagnostic"),

    AIR_STATUS_UNKNOWN(0u, "Unknown Air Status"),
    ;

    companion object
    {
        private val map: Map<ULong, AirStatuses> = AirStatuses.entries.associateBy(AirStatuses::num)
        operator fun get(num: ULong) = map[num] ?: AIR_STATUS_UNKNOWN
    }
}