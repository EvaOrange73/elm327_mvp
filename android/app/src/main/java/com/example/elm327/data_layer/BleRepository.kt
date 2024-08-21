package com.example.elm327.data_layer

import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.MacAddress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface BleRepository {
    val uiState: StateFlow<BleState>
}

class BleRepositoryImp private constructor() : BleRepository {
    private val LOG_TAG = "Ble Repository"

    private val _uiState = MutableStateFlow(BleState())
    override val uiState = _uiState.asStateFlow()

    companion object {
        @Volatile
        private var instance: BleRepositoryImp? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: BleRepositoryImp().also { instance = it }
        }
    }


    fun updateScanState(scanState: ScanState){
        _uiState.update {
            it.copy(scanState = scanState)
        }
    }

    fun updateDeviceList(deviceList: List<Device>) {
        _uiState.update {
            it.copy(deviceList = deviceList)
        }
    }

    fun updateSelectedMacAddress(macAddress: MacAddress){
        _uiState.update {
            it.copy(selectedMacAddress = macAddress)
        }
    }


    fun updateConnectionState(connectionState: ConnectionState){
        _uiState.update {
            it.copy(connectionState = connectionState)
        }
    }
}