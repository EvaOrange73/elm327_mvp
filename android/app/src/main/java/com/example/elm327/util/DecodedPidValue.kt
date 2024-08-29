package com.example.elm327.util

import com.example.elm327.util.elm.ObdPids
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
}