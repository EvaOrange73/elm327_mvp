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
        fun getDefault(source: String = "last"): MacAddress {
            // TODO сохранять в файл последний выбранный мак адрес как дефолтный
            return when (source) {
                "last" -> MacAddress("00:10:CC:4F:36:03")
                "синий" -> MacAddress("00:10:CC:4F:36:03")
                "красный" -> MacAddress("22:C0:00:03:61:2E")
                else -> MacAddress(source)
            }
        }
    }
}