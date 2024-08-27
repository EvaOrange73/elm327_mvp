package com.example.elm327.util.value

class Time private constructor(private val timeSI: Double) : Value()
{
    override val value: Double = timeSI

    companion object
    {
        fun seconds(timeSeconds: Double) : Time
        {
            return Time(timeSeconds)
        }

        fun minutes(timeMinutes: Double) : Time
        {
            return Time(timeMinutes * 60)
        }

        fun hours(timeHours: Double) : Time
        {
            return Time(timeHours * 3600)
        }
    }

    fun getMinutes() : Double
    {
        return value / 60
    }

    fun getHours() : Double
    {
        return value / 3600
    }

    override fun printerSI() : String
    {
        return "${getSI()} s"
    }

    fun printerMinutes() : String
    {
        return "${getMinutes()} min"
    }

    fun printerHours() : String
    {
        return "${getHours()} h"
    }
}

class Distance private constructor(private val distanceSI: Double) : Value()
{
    override val value: Double = distanceSI

    companion object
    {
        fun meters(distanceMeters: Double) : Distance
        {
            return Distance(distanceMeters)
        }

        fun kiloMeters(distanceKiloMeters: Double) : Distance
        {
            return Distance(distanceKiloMeters * 1000)
        }

        fun miles(distanceMiles: Double) : Distance
        {
            return Distance(distanceMiles * 1609.344)
        }
    }

    fun getKiloMeters() : Double
    {
        return value / 1000
    }

    fun getMiles() : Double
    {
        return value / 1609.344
    }

    override fun printerSI() : String
    {
        return "${getSI()} m"
    }

    fun printerKiloMeters() : String
    {
        return "${getKiloMeters()} km"
    }

    fun printerMiles() : String
    {
        return "${getMiles()} mil"
    }
}

class Temperature private constructor(private val temperatureSI: Double) : Value()
{
    override val value: Double = temperatureSI

    companion object
    {
        fun kelvins(temperatureKelvins: Double) : Temperature
        {
            return Temperature(temperatureKelvins)
        }

        fun celsius(temperatureCelsius: Double) : Temperature
        {
            return Temperature(temperatureCelsius + 273.15)
        }

        fun fahrenheit(temperatureFahrenheit: Double) : Temperature
        {
            return Temperature((temperatureFahrenheit + 459.67) * 5 / 9)
        }
    }

    fun getCelsius() : Double
    {
        return value - 273.15
    }

    fun getFahrenheit() : Double
    {
        return value * 9 / 5 - 459.67
    }

    override fun printerSI() : String
    {
        return "${getSI()} K"
    }

    fun printerCelsius() : String
    {
        return "${getCelsius()} C"
    }

    fun printerFahrenheit() : String
    {
        return "${getFahrenheit()} F"
    }
}

class ElectricCurrent private constructor(private val electricCurrentSI: Double) : Value()
{
    override val value: Double = electricCurrentSI

    companion object
    {
        fun ampere(electricCurrentAmpere: Double) : ElectricCurrent
        {
            return ElectricCurrent(electricCurrentAmpere)
        }

        fun milliAmpere(electricCurrentMilliAmpere: Double) : ElectricCurrent
        {
            return ElectricCurrent(electricCurrentMilliAmpere / 1000)
        }
    }

    fun getMilliAmpere() : Double
    {
        return value * 1000
    }

    override fun printerSI() : String
    {
        return "${getSI()} A"
    }

    fun printerMilliAmpere() : String
    {
        return "${getMilliAmpere()} mA"
    }
}
