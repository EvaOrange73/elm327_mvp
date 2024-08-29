package com.example.elm327.data_layer

import android.location.Location
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Value

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

enum class SyncState {
    NO_PERMISSIONS,
    SYNCHRONIZED,
    NOT_SYNCHRONIZED,
}

data class BleState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: DeviceList = DeviceList(),

    val selectedMacAddress: MacAddress = MacAddress.getDefault(),
    val connectionState: ConnectionState = ConnectionState.NO_PERMISSIONS,

    val syncState: SyncState = SyncState.NOT_SYNCHRONIZED, // TODO: check permissions
    val carId: String? = null,
    val pidValues: Map<ObdPids, DecodedPidValue> = mapOf(),

    val location: Location? = null,
)