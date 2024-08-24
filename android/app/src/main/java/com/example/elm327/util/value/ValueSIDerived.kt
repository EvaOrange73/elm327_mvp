package com.example.elm327.util.value

class Speed(private val speedSI: Double) : Value()
{
    override val value: Double = speedSI

    companion object
    {
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

class Frequency(private val frequencySI: Double) : Value()
{
    override val value: Double = frequencySI

    companion object
    {
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

class Pressure(private val pressureSI: Double) : Value()
{
    override val value: Double = pressureSI

    companion object
    {
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

class Voltage(private val voltageSI: Double) : Value()
{
    override val value: Double = voltageSI

    companion object
    {
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

class MassFlow(private val flowSI: Double) : Value()
{
    override val value: Double = flowSI

    companion object
    {
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

class VolumeFlow(private val flowSI: Double) : Value()
{
    override val value: Double = flowSI

    companion object
    {
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
