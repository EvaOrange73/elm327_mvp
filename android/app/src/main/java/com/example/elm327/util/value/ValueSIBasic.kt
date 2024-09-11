package com.example.elm327.util.value

import com.example.elm327.data_layer.UnitOfMeasurement
import java.math.RoundingMode

class Angle private constructor(private val angleSI: Double) : Value()
{
    override val value: Double = angleSI

    companion object
    {
        fun radian(angleRadian: Double) : Angle
        {
            return Angle(angleRadian)
        }

        fun degree(angleDegrees: Double) : Angle
        {
            return Angle(angleDegrees / 57.296)
        }
    }

    override fun printerSI(): String
    {
        return "${getRadians().toBigDecimal().setScale(roundNumbers, roundMode)} rad"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerDegrees()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerDegrees()
            UnitOfMeasurement.IMPERIAL       -> printerDegrees()
        }
    }

    fun getRadians() : Double
    {
        return value
    }

    fun getDegrees() : Double
    {
        return value * 57.296
    }

    fun printerRadians() : String
    {
        return "${getRadians().toBigDecimal().setScale(roundNumbers, roundMode)} rad"
    }

    fun printerDegrees() : String
    {
        return "${getDegrees().toBigDecimal().setScale(roundNumbers, roundMode)} °"
    }
}

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

    override fun printerSI(): String
    {
        return "${getSeconds().toBigDecimal().setScale(roundNumbers, roundMode)} s"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerSeconds()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getHours() >= 1.0) printerHours() else if (getMinutes() >= 1.0) printerMinutes() else printerSeconds()
            UnitOfMeasurement.IMPERIAL       -> printerSeconds()
        }
    }

    fun getSeconds() : Double
    {
        return value
    }

    fun getMinutes() : Double
    {
        return value / 60
    }

    fun getHours() : Double
    {
        return value / 3600
    }

    fun printerSeconds() : String
    {
        return "${getSeconds().toBigDecimal().setScale(roundNumbers, roundMode)} s"
    }

    fun printerMinutes() : String
    {
        return "${getMinutes().toBigDecimal().setScale(roundNumbers, roundMode)} min"
    }

    fun printerHours() : String
    {
        return "${getHours().toBigDecimal().setScale(roundNumbers, roundMode)} h"
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

    override fun printerSI(): String
    {
        return "${getMeters().toBigDecimal().setScale(roundNumbers, roundMode)} m"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerMeters()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getKiloMeters() >= 1.0) printerKiloMeters() else printerMeters()
            UnitOfMeasurement.IMPERIAL       -> printerMiles()
        }
    }

    fun getMeters(): Double
    {
        return value
    }

    fun getKiloMeters() : Double
    {
        return value / 1000
    }

    fun getMiles() : Double
    {
        return value / 1609.344
    }

    fun printerMeters() : String
    {
        return "${getMeters().toBigDecimal().setScale(roundNumbers, roundMode)} m"
    }

    fun printerKiloMeters() : String
    {
        return "${getKiloMeters().toBigDecimal().setScale(roundNumbers, roundMode)} km"
    }

    fun printerMiles() : String
    {
        return "${getMiles().toBigDecimal().setScale(roundNumbers, roundMode)} mil"
    }
}

class Mass private constructor(private val massSI: Double) : Value()
{
    override val value: Double = massSI

    companion object
    {
        fun kiloGrams(massKiloGrams: Double) : Mass
        {
            return Mass(massKiloGrams)
        }

        fun grams(massGrams: Double) : Mass
        {
            return Mass(massGrams / 1000)
        }

        fun tonnes(massTonnes: Double) : Mass
        {
            return Mass(massTonnes * 1000)
        }

        fun pounds(massPounds: Double) : Mass
        {
            return Mass(massPounds * 0.4536)
        }
    }

    override fun printerSI(): String
    {
        return "${getKiloGrams().toBigDecimal().setScale(roundNumbers, roundMode)} kg"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerKiloGrams()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getTonnes() >= 1.0) printerTonnes() else if (getKiloGrams() >= 1.0) printerKiloGrams() else printerGrams()
            UnitOfMeasurement.IMPERIAL       -> printerPounds()
        }
    }

    fun getKiloGrams() : Double
    {
        return value
    }

    fun getGrams() : Double
    {
        return value * 1000
    }

    fun getTonnes() : Double
    {
        return value / 1000
    }

    fun getPounds() : Double
    {
        return value / 0.4536
    }

    fun printerKiloGrams() : String
    {
        return "${getKiloGrams().toBigDecimal().setScale(roundNumbers, roundMode)} kg"
    }

    fun printerGrams() : String
    {
        return "${getGrams().toBigDecimal().setScale(roundNumbers, roundMode)} g"
    }

    fun printerTonnes() : String
    {
        return "${getTonnes().toBigDecimal().setScale(roundNumbers, roundMode)} t"
    }

    fun printerPounds() : String
    {
        return "${getPounds().toBigDecimal().setScale(roundNumbers, roundMode)} lb"
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

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerCelsius()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerCelsius()
            UnitOfMeasurement.IMPERIAL       -> printerFahrenheit()
        }
    }

    override fun printerSI(): String
    {
        return "${getKelvins().toBigDecimal().setScale(roundNumbers, roundMode)} K"
    }

    fun getKelvins() : Double
    {
        return value
    }

    fun getCelsius() : Double
    {
        return value - 273.15
    }

    fun getFahrenheit() : Double
    {
        return value * 9 / 5 - 459.67
    }

    fun printerKelvins() : String
    {
        return "${getKelvins().toBigDecimal().setScale(roundNumbers, roundMode)} K"
    }

    fun printerCelsius() : String
    {
        return "${getCelsius().toBigDecimal().setScale(roundNumbers, roundMode)} °C"
    }

    fun printerFahrenheit() : String
    {
        return "${getFahrenheit().toBigDecimal().setScale(roundNumbers, roundMode)} °F"
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

    override fun printerSI(): String
    {
        return "${getAmpere().toBigDecimal().setScale(roundNumbers, roundMode)} A"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerAmpere()
            UnitOfMeasurement.METRIC_OPTIMAL -> if (getAmpere() >= 1.0) printerAmpere() else printerMilliAmpere()
            UnitOfMeasurement.IMPERIAL       -> printerAmpere()
        }
    }

    fun getAmpere() : Double
    {
        return value
    }

    fun getMilliAmpere() : Double
    {
        return value * 1000
    }

    fun printerAmpere() : String
    {
        return "${getAmpere().toBigDecimal().setScale(roundNumbers, roundMode)} A"
    }

    fun printerMilliAmpere() : String
    {
        return "${getMilliAmpere().toBigDecimal().setScale(roundNumbers, roundMode)} mA"
    }
}
