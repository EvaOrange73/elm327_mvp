package com.example.elm327.util.value

import com.example.elm327.data_layer.UnitOfMeasurement

class Velocity private constructor(private val velocitySI: Double) : Value()
{
    override val value: Double = velocitySI

    companion object
    {
        fun metersPerSecond(velocityMetersPerSecond: Double) : Velocity
        {
            return Velocity(velocityMetersPerSecond)
        }

        fun kiloMetersPerHour(velocityKiloMetersPerHour: Double) : Velocity
        {
            return Velocity(velocityKiloMetersPerHour / 3.6)
        }

        fun milesPerHour(velocityMilesMetersPerHour: Double) : Velocity
        {
            return Velocity(velocityMilesMetersPerHour / 2.2369)
        }
    }

    override fun printerSI(): String
    {
        return "${getMetersPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} m⋅s^-1"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerKiloMetersPerHour()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerKiloMetersPerHour()
            UnitOfMeasurement.IMPERIAL       -> printerMilesPerHour()
        }
    }

    fun getMetersPerSecond() : Double
    {
        return value
    }

    fun getKiloMetersPerHour() : Double
    {
        return value * 3.6
    }

    fun getMilesPerHour() : Double
    {
        return value * 2.2369
    }

    fun printerMetersPerSecond() : String
    {
        return "${getMetersPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} m/s"
    }

    fun printerKiloMetersPerHour() : String
    {
        return "${getKiloMetersPerHour().toBigDecimal().setScale(roundNumbers, roundMode)} km/h"
    }

    fun printerMilesPerHour() : String
    {
        return "${getMilesPerHour().toBigDecimal().setScale(roundNumbers, roundMode)} mil/h"
    }
}

class Frequency private constructor(private val frequencySI: Double) : Value()
{
    override val value: Double = frequencySI

    companion object
    {
        fun revolutionsPerSecond(frequencyRevolutionsPerSecond: Double) : Frequency
        {
            return Frequency(frequencyRevolutionsPerSecond)
        }

        fun revolutionsPerMinute(frequencyRevolutionsPerMinute: Double) : Frequency
        {
            return Frequency(frequencyRevolutionsPerMinute / 60)
        }

        fun revolutionsPerHour(frequencyRevolutionsPerHour: Double) : Frequency
        {
            return Frequency(frequencyRevolutionsPerHour / 3600)
        }
    }

    override fun printerSI(): String
    {
        return "${getRevolutionsPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} s^-1"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerRevolutionsPerSecond()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerRevolutionsPerMinute()
            UnitOfMeasurement.IMPERIAL       -> printerRevolutionsPerMinute()
        }
    }

    fun getRevolutionsPerSecond() : Double
    {
        return value
    }

    fun getRevolutionsPerMinute() : Double
    {
        return value * 60
    }

    fun getRevolutionsPerHour() : Double
    {
        return value * 3600
    }

    fun printerRevolutionsPerSecond() : String
    {
        return "${getRevolutionsPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} rps"
    }

    fun printerRevolutionsPerMinute() : String
    {
        return "${getRevolutionsPerMinute().toBigDecimal().setScale(roundNumbers, roundMode)} rpm"
    }

    fun printerRevolutionsPerHour() : String
    {
        return "${getRevolutionsPerHour().toBigDecimal().setScale(roundNumbers, roundMode)} rph"
    }
}

class Pressure private constructor(private val pressureSI: Double) : Value()
{
    override val value: Double = pressureSI

    companion object
    {
        fun pascal(pressurePascal: Double) : Pressure
        {
            return Pressure(pressurePascal)
        }

        fun kiloPascal(pressureKiloPascal: Double) : Pressure
        {
            return Pressure(pressureKiloPascal * 1000)
        }
    }

    override fun printerSI(): String
    {
        return "${getPascal().toBigDecimal().setScale(roundNumbers, roundMode)} kg⋅m^-1⋅s^-2"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerPascal()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getKiloPascal() >= 1.0) printerKiloPascal() else printerPascal()
            UnitOfMeasurement.IMPERIAL       -> printerPascal()
        }
    }

    fun getPascal() : Double
    {
        return value
    }

    fun getKiloPascal() : Double
    {
        return value / 1000
    }

    fun printerPascal() : String
    {
        return "${getPascal().toBigDecimal().setScale(roundNumbers, roundMode)} Pa"
    }

    fun printerKiloPascal() : String
    {
        return "${getKiloPascal().toBigDecimal().setScale(roundNumbers, roundMode)} kPa"
    }
}

class Voltage private constructor(private val voltageSI: Double) : Value()
{
    override val value: Double = voltageSI

    companion object
    {
        fun volts(voltageVolts: Double) : Voltage
        {
            return Voltage(voltageVolts)
        }

        fun milliVolts(voltageMilliVolts: Double) : Voltage
        {
            return Voltage(voltageMilliVolts / 1000)
        }
    }

    override fun printerSI(): String
    {
        return "${getVolts().toBigDecimal().setScale(roundNumbers, roundMode)} kg⋅m^2⋅s^-3⋅A^-1"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerVolts()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getVolts() >= 1.0) printerVolts() else printerMilliVolts()
            UnitOfMeasurement.IMPERIAL       -> printerVolts()
        }
    }

    fun getVolts() : Double
    {
        return value
    }

    fun getMilliVolts() : Double
    {
        return value * 1000
    }

    fun printerVolts() : String
    {
        return "${getVolts().toBigDecimal().setScale(roundNumbers, roundMode)} V"
    }

    fun printerMilliVolts() : String
    {
        return "${getMilliVolts().toBigDecimal().setScale(roundNumbers, roundMode)} mV"
    }
}

class MassFlow private constructor(private val flowSI: Double) : Value()
{
    override val value: Double = flowSI

    companion object
    {
        fun kiloGramsPerSecond(flowKiloGramsPerSecond: Double) : MassFlow
        {
            return MassFlow(flowKiloGramsPerSecond)
        }

        fun gramsPerSecond(flowGramsPerSecond: Double) : MassFlow
        {
            return MassFlow(flowGramsPerSecond / 1000)
        }
    }

    override fun printerSI(): String
    {
        return "${getKiloGramsPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} kg⋅s^-1"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerGramsPerSecond()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getKiloGramsPerSecond() >= 1.0) printerKiloGramsPerSecond() else printerGramsPerSecond()
            UnitOfMeasurement.IMPERIAL       -> printerGramsPerSecond()
        }
    }

    fun getKiloGramsPerSecond() : Double
    {
        return value
    }

    fun getGramsPerSecond() : Double
    {
        return value * 1000
    }

    fun printerKiloGramsPerSecond() : String
    {
        return "${getKiloGramsPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} kg/s"
    }

    fun printerGramsPerSecond() : String
    {
        return "${getGramsPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} g/s"
    }
}

class VolumeFlow private constructor(private val flowSI: Double) : Value()
{
    override val value: Double = flowSI

    companion object
    {
        fun cubicMetersPerSecond(flowCubicMetersPerSecond: Double) : VolumeFlow
        {
            return VolumeFlow(flowCubicMetersPerSecond)
        }

        fun litersPerSecond(flowLitersPerSecond: Double) : VolumeFlow
        {
            return VolumeFlow(flowLitersPerSecond / 1000)
        }
    }

    override fun printerSI(): String
    {
        return "${getCubicMetersPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} m^3⋅s^-1"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerLitersPerSecond()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getCubicMetersPerSecond() >= 1.0) printerCubicMetersPerSecond() else printerLitersPerSecond()
            UnitOfMeasurement.IMPERIAL       -> printerLitersPerSecond()
        }
    }

    fun getCubicMetersPerSecond() : Double
    {
        return value
    }

    fun getLitersPerSecond() : Double
    {
        return value * 1000
    }

    fun printerCubicMetersPerSecond() : String
    {
        return "${getCubicMetersPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} m^3/s"
    }

    fun printerLitersPerSecond() : String
    {
        return "${getLitersPerSecond().toBigDecimal().setScale(roundNumbers, roundMode)} L/s"
    }
}

class Torque private constructor(private val torqueSI: Double) : Value()
{
    override val value: Double = torqueSI

    companion object
    {
        fun newtonMeters(torqueNewtonMeters: Double) : Torque
        {
            return Torque(torqueNewtonMeters)
        }

        fun poundFoot(torquePoundFoot: Double) : Torque
        {
            return Torque(torquePoundFoot * 1.355817)
        }
    }

    override fun printerSI(): String
    {
        return "${getNewtonMeters().toBigDecimal().setScale(roundNumbers, roundMode)} kg⋅m^2⋅s^-2"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerNewtonMeters()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerNewtonMeters()
            UnitOfMeasurement.IMPERIAL       -> printerPoundFoot()
        }
    }

    fun getNewtonMeters() : Double
    {
        return value
    }

    fun getPoundFoot() : Double
    {
        return value / 1.355817
    }

    fun printerNewtonMeters() : String
    {
        return "${getNewtonMeters().toBigDecimal().setScale(roundNumbers, roundMode)} N⋅m"
    }

    fun printerPoundFoot() : String
    {
        return "${getPoundFoot().toBigDecimal().setScale(roundNumbers, roundMode)} lbf⋅ft"
    }
}
