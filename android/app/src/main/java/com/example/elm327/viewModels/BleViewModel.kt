package com.example.elm327.viewModels

import androidx.lifecycle.ViewModel
import com.example.elm327.data.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

class BleState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: List<Device> = listOf()
)

class BleViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(BleState())
    val uiState: StateFlow<BleState> = _uiState.asStateFlow()

    fun startScan() {
        _uiState.update { currentState ->
            BleState(ScanState.SCANNING, currentState.deviceList)
        }
    }

    fun stopScan() {
        _uiState.update { currentState ->
            BleState(ScanState.READY_TO_SCAN, currentState.deviceList)
        }
    }

    fun setScanningResult(deviceList: List<Device>) {
        _uiState.update {
            BleState(ScanState.READY_TO_SCAN, deviceList)
        }
    }
}