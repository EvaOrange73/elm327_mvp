package com.example.elm327.util

import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Bool
import com.example.elm327.util.value.Value


data class DecodedPidValue(
    val timestamp: Long,
    val rawData: String,
    val pid: ObdPids,
    val values: List<Value>,
) {
    fun valuesAsString(): String {
        return values.joinToString(",") { it.printerSI() }
    }

    private fun isPidGetter(): Boolean {
        return this.pid in listOf(ObdPids.PID_00, ObdPids.PID_20, ObdPids.PID_40, ObdPids.PID_60)
    }

    fun valuesAsPidGetter(): String {
        if (this.isPidGetter()) {
            var str = ""
            values.forEach {
                if ((it as Bool).getSI() as Boolean) str += "${it.getIndex()} "
            }
            return str
        } else return ""
    }
}