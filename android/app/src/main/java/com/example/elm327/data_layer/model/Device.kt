package com.example.elm327.data_layer.model


class DeviceList {
    private val devices = mutableListOf(
        Device(MacAddress.getDefault())
    )

    fun add(device: Device) {
        devices.add(device)
    }

    fun findDeviceByMacAddress(requiredAddress: MacAddress): Device? {
        for (device in devices) {
            if (device.address == requiredAddress)
                return device
        }
        return null
    }

    fun indexOf(requiredAddress: MacAddress): Int {
        return devices.indexOf(devices.find { it.address == requiredAddress })
    }

    fun getStringList(): List<String> {
        return devices.map { it.toString() }
    }

    override fun toString(): String {
        return this.getStringList().joinToString(", ")
    }
}

class Device(val address: MacAddress, val name: String = "") {

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is Device && this.address == other.address) {
            return true
        }
        return false
    }

    override fun toString(): String {
        return if (name != "") name else address.toString()
    }

    override fun hashCode(): Int {
        return address.hashCode()
    }
}