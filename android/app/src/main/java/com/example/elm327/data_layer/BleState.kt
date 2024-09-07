package com.example.elm327.data_layer

import android.location.Location
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import java.util.SortedMap

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

enum class UnitOfMeasurement(val label: String) {
    SI("SI"),
    METRIC("metric"),
    METRIC_OPTIMAL("optimal"),
    IMPERIAL("imperial"),
    ;

    fun next(): UnitOfMeasurement {
        val values = entries.toTypedArray()
        val nextOrdinal = (ordinal + 1) % values.size
        return values[nextOrdinal]
    }
}

data class BleState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: DeviceList = DeviceList(),

    val selectedMacAddress: MacAddress = MacAddress.getDefault(),
    val connectionState: ConnectionState = ConnectionState.NO_PERMISSIONS,

    val syncState: SyncState = SyncState.NOT_SYNCHRONIZED, // TODO: check permissions
    val carId: String? = null,
    val pidValues: SortedMap<ObdPids, DecodedPidValue> = sortedMapOf(comparator = compareBy<ObdPids> { it.pid }),

    val location: Location? = null,

    val unitOfMeasurement: UnitOfMeasurement = UnitOfMeasurement.METRIC_OPTIMAL,
)