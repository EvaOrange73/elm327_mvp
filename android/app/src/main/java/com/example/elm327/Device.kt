package com.example.elm327
import android.text.BoringLayout
import com.example.elm327.elm.MacAddress

class Device(address: MacAddress, name: String = "") {
    private val address: MacAddress = address
    private val name: String = name

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