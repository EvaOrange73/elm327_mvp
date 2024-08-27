package com.example.elm327.util.value

abstract class Value
{
    abstract val value: Any

    fun getSI() : Any
    {
        return value
    }

    open fun printerSI() : String
    {
        return value.toString()
    }

    override fun toString(): String
    {
        return printerSI()
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

    override fun printerSI() : String
    {
        return "${getSI()}"
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

    override fun printerSI() : String
    {
        return "${if (index == null) "" else "$index - "} ${if (value) "On" else "Off"}"
    }
}

class Ratio private constructor(private val ratioSI: Double) : Value()
{
    override val value: Double = ratioSI

    companion object
    {
        fun ratio(ratioRaw: Double) : Ratio
        {
            return Ratio(ratioRaw)
        }

        fun percents(ratioPercents: Double) : Ratio
        {
            return Ratio(ratioPercents / 100)
        }

        fun degree(ratioDegrees: Double) : Ratio
        {
            return Ratio(ratioDegrees / 57.296)
        }
    }

    fun getPercents() : Double
    {
        return value * 100
    }

    fun getDegrees() : Double
    {
        return value * 57.296
    }

    override fun printerSI() : String
    {
        return "${getSI()}"
    }

    fun printerPercents() : String
    {
        return "${getPercents()} %"
    }

    fun printerDegrees() : String
    {
        return "${getDegrees()} °"
    }
}
