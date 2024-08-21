package com.example.elm327.data_layer

import com.example.elm327.data_layer.model.Device

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
}

data class BleState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: List<Device> = listOf()
)