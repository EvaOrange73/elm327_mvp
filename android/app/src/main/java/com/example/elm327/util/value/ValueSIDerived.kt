package com.example.elm327.util.value

class Speed private constructor(private val speedSI: Double) : Value()
{
    override val value: Double = speedSI

    companion object
    {
        fun metersPerSecond(speedMetersPerSecond: Double) : Speed
        {
            return Speed(speedMetersPerSecond)
        }

        fun kiloMetersPerHour(speedKiloMetersPerHour: Double) : Speed
        {
            return Speed(speedKiloMetersPerHour / 3.6)
        }

        fun milesPerHour(speedMilesMetersPerHour: Double) : Speed
        {
            return Speed(speedMilesMetersPerHour / 2.2369)
        }
    }

    fun getKiloMetersPerHour() : Double
    {
        return value * 3.6
    }

    fun getMilesPerHour() : Double
    {
        return value * 2.2369
    }

    override fun printerSI() : String
    {
        return "${getSI()} m/s"
    }

    fun printerKiloMetersPerHour() : String
    {
        return "${getKiloMetersPerHour()} km/h"
    }

    fun printerKiloPascal() : String
    {
        return "${getMilesPerHour()} mil/h"
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

    fun getRevolutionsPerMinute() : Double
    {
        return value * 60
    }

    fun getRevolutionsPerHour() : Double
    {
        return value * 3600
    }

    override fun printerSI() : String
    {
        return "${getSI()} s^-1"
    }

    fun printerRevolutionsPerMinute() : String
    {
        return "${getRevolutionsPerMinute()} rpm"
    }

    fun printerRevolutionsPerHour() : String
    {
        return "${getRevolutionsPerHour()} rph"
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

    fun getKiloPascal() : Double
    {
        return value / 1000
    }

    override fun printerSI() : String
    {
        return "${getSI()} Pa"
    }

    fun printerKiloPascal() : String
    {
        return "${getKiloPascal()} kPa"
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

    fun getMilliVolts() : Double
    {
        return value * 1000
    }

    override fun printerSI() : String
    {
        return "${getSI()} V"
    }

    fun printerMilliVolts() : String
    {
        return "${getMilliVolts()} mV"
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

    fun getGramsPerSecond() : Double
    {
        return value * 1000
    }

    override fun printerSI() : String
    {
        return "${getSI()} kg/s"
    }

    fun printerGramsPerSecond() : String
    {
        return "${getGramsPerSecond()} g/s"
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

    fun getLitersPerSecond() : Double
    {
        return value * 1000
    }

    override fun printerSI() : String
    {
        return "${getSI()} m^3/s"
    }

    fun printerGramsPerSecond() : String
    {
        return "${getLitersPerSecond()} L/s"
    }
}
