package com.example.elm327.util.value

import com.example.elm327.data_layer.UnitOfMeasurement
import java.math.RoundingMode

abstract class Value
{
    abstract val value: Any

    companion object
    {
        val roundNumbers = 2
        val roundMode = RoundingMode.UP
    }

    fun getSI() : Any
    {
        return value
    }

    open fun printerSI() : String
    {
        return "${getSI()}"
    }

    open fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return value.toString()
    }

    override fun toString(): String
    {
        return value.toString()
    }
}

class RawData private constructor(private val data: String) : Value()
{
    override val value: String = data

    companion object
    {
        fun raw(rawData: String) : RawData
        {
            return RawData(rawData)
        }
    }

    override fun printerSI(): String
    {
        return "${getSI()}"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return "${getSI()}"
    }

    fun getRaw() : String
    {
        return value
    }
}

class Bool private constructor(private val bool: Boolean, private val index: String? = null) : Value()
{
    override val value: Boolean = bool

    companion object
    {
        fun boolRaw(boolean: Boolean) : Bool
        {
            return Bool(boolean)
        }

        fun boolIndexed(boolean: Boolean, index: String) : Bool
        {
            return Bool(boolean, index)
        }
    }

    override fun printerSI(): String
    {
        return "${getBool()}"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        return if (unitOfMeasurement == UnitOfMeasurement.SI) printerSI() else "${if (index == null) "" else "$index - "} ${if (value) "On" else "Off"}"
    }

    fun getBool() : Boolean
    {
        return value
    }

    fun getIndex(): String?
    {
        return index
    }
}

class Ratio private constructor(private val ratioSI: Double, private val rawOnly: Boolean = false) : Value()
{
    override val value: Double = ratioSI

    companion object
    {
        fun ratio(ratioRaw: Double) : Ratio
        {
            return Ratio(ratioRaw, true)
        }

        fun percents(ratioPercents: Double) : Ratio
        {
            return Ratio(ratioPercents / 100)
        }
    }

    override fun printerSI(): String
    {
        return "${getRatio().toBigDecimal().setScale(roundNumbers, roundMode)}"
    }

    override fun printer(unitOfMeasurement: UnitOfMeasurement) : String
    {
        if (rawOnly) return printerSI()
        return when (unitOfMeasurement)
        {
            UnitOfMeasurement.SI             -> printerSI()
            UnitOfMeasurement.METRIC         -> printerPercents()
            UnitOfMeasurement.METRIC_OPTIMAL -> printerPercents()
            UnitOfMeasurement.IMPERIAL       -> printerPercents()
        }
    }

    fun getRatio() : Double
    {
        return value
    }

    fun getPercents() : Double
    {
        return value * 100
    }

    fun printerRatio() : String
    {
        return "${getRatio().toBigDecimal().setScale(roundNumbers, roundMode)}"
    }

    fun printerPercents() : String
    {
        return "${getPercents().toBigDecimal().setScale(roundNumbers, roundMode)} %"
    }
}
