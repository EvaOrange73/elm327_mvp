package com.example.elm327.elm

import com.example.elm327.Device

class MacAddress(source: String) {
    private val validatedMacAddress: String

    init { //TODO: validation
        when (source) {
            "синий" -> {
                this.validatedMacAddress = "00:10:CC:4F:36:03"
            }

            "красный" -> {
                this.validatedMacAddress = "22:C0:00:03:61:2E"
            }

            else -> {
                this.validatedMacAddress = source
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is MacAddress && this.validatedMacAddress == other.validatedMacAddress) {
            return true
        }
        return false
    }

    override fun toString(): String {
        return validatedMacAddress
    }

}