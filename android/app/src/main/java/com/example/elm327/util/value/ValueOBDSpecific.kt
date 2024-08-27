package com.example.elm327.util.value

class FuelType(private val fuelType: FuelTypes) : Value()
{
    override val value: FuelTypes = fuelType

    companion object
    {
        fun fromULong(number: ULong) : FuelType
        {
            return FuelType(FuelTypes[number])
        }
    }

    override fun printerSI() : String
    {
        return value.description
    }
}

class OBDStandard(private val OBDStandard: OBDStandards) : Value()
{
    override val value: OBDStandards = OBDStandard

    companion object
    {
        fun fromULong(number: ULong) : OBDStandard
        {
            return OBDStandard(OBDStandards[number])
        }
    }

    override fun printerSI() : String
    {
        return value.description
    }
}

class FuelSystemStatus(private val fuelSystemStatus: FuelSystemStatuses) : Value()
{
    override val value: FuelSystemStatuses = fuelSystemStatus

    companion object
    {
        fun fromULong(number: ULong) : FuelSystemStatus
        {
            return FuelSystemStatus(FuelSystemStatuses[number])
        }
    }

    override fun printerSI() : String
    {
        return value.description
    }
}

class AirStatus(private val airStatus: AirStatuses) : Value()
{
    override val value: AirStatuses = airStatus

    companion object
    {
        fun fromULong(number: ULong) : AirStatus
        {
            return AirStatus(AirStatuses[number])
        }
    }

    override fun printerSI() : String
    {
        return value.description
    }
}