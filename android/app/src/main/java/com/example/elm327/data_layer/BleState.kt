package com.example.elm327.data_layer

import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.elm.DecodedValue
import com.example.elm327.util.elm.ObdPids

enum class ScanState {
    NO_PERMISSIONS,
    READY_TO_SCAN,
    SCANNING,
}

enum class ConnectionState {
    NO_PERMISSIONS,
    READY_TO_CONNECT,
    CONNECTING,
    CONNECTED,
    FAIL
}

data class BleState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: List<Device> = listOf(Device(MacAddress.default, "синий")),
    val selectedMacAddress: MacAddress = MacAddress.default,
    val connectionState: ConnectionState = ConnectionState.NO_PERMISSIONS,
    val pidValues: MutableMap<ObdPids, DecodedValue> = mutableMapOf(),
)