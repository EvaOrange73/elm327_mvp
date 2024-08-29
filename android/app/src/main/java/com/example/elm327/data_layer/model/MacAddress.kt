package com.example.elm327.data_layer.model

class MacAddress(private val macAddress: String) {

    override fun toString(): String {
        return macAddress
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is MacAddress && this.macAddress == other.macAddress) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        return macAddress.hashCode()
    }

    companion object {
        val preferenceKey: String = "macAddressDefault"
        private var default: String = "00:10:CC:4F:36:03"
        private val blue: String = "00:10:CC:4F:36:03"
        private var red: String = "22:C0:00:03:61:2E"

        fun setDefault(value: String) {
            default = value
        }

        fun getDefault(source: String = "last"): MacAddress {
            return when (source) {
                "last" -> MacAddress(default)
                "синий" -> MacAddress(blue)
                "красный" -> MacAddress(red)
                else -> MacAddress(source)
            }
        }
    }
}