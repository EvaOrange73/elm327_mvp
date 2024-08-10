package com.example.elm327.elm

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
                this.validatedMacAddress = ""
            }
        }
    }

    public fun getAsString(): String {
        return validatedMacAddress
    }

}