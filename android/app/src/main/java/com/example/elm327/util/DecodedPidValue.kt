package com.example.elm327.util

import com.example.elm327.data_layer.UnitOfMeasurement
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Bool
import com.example.elm327.util.value.Value


data class DecodedPidValue(
    val timestamp: Long,
    val rawData: String,
    val pid: ObdPids,
    val values: List<Value>,
) {
    fun valuesAsString( unitOfMeasurement: UnitOfMeasurement): String {
        return values.joinToString(",") { it.printer(unitOfMeasurement) }
    }

    fun valuesAsPidGetter(): String {
        if (pid in ObdPids.getters) {
            var str = ""
            values.forEach {
                if ((it as Bool).getSI() as Boolean) str += "${it.getIndex()} "
            }
            return str
        } else return ""
    }
}