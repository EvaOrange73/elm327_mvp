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

class RawData(private val data: String) : Value()
{
    override val value: String = data

    override fun printerSI() : String
    {
        return "${getSI()}"
    }
}

class Bool(private val bool: Boolean, private val number: String? = null) : Value()
{
    override val value: Boolean = bool

    override fun printerSI() : String
    {
        return "${if (number == null) "" else "$number - "} ${if (value) "On" else "Off"}"
    }
}

class Ratio(private val ratioSI: Double) : Value()
{
    override val value: Double = ratioSI

    companion object
    {
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
