package com.example.elm327.data

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
        return address.toString()
    }
}