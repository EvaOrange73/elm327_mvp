package com.example.elm327.util.value

class FuelType(private val fuelType: FuelTypes) : Value()
{
    override val value: FuelTypes = fuelType

    override fun printerSI() : String
    {
        return value.description
    }
}

class OBDStandard(private val OBDStandard: OBDStandards) : Value()
{
    override val value: OBDStandards = OBDStandard

    override fun printerSI() : String
    {
        return value.description
    }
}

class FuelSystemStatus(private val fuelSystemStatus: FuelSystemStatuses) : Value()
{
    override val value: FuelSystemStatuses = fuelSystemStatus

    override fun printerSI() : String
    {
        return value.description
    }
}

class AirStatus(private val airStatus: AirStatuses) : Value()
{
    override val value: AirStatuses = airStatus

    override fun printerSI() : String
    {
        return value.description
    }
}