package com.example.elm327.ui_layer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elm327.data_layer.BleRepository
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ScanFragmentViewState(
    val scanState: ScanState = ScanState.NO_PERMISSIONS,
    val deviceList: DeviceList = DeviceList(),
    val selectedMacAddress: MacAddress = MacAddress.getDefault(),
    val connectionState: ConnectionState = ConnectionState.NO_PERMISSIONS,
)

class ScanFragmentViewModel(private val bleRepository: BleRepository) : ViewModel() {
    val LOG_TAG = "Ble View Model"

    private val _uiState = MutableStateFlow(ScanFragmentViewState())
    val uiState = _uiState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val bleRepository: BleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ScanFragmentViewModel(bleRepository) as T
        }
    }

    init {
        viewModelScope.launch {
            bleRepository.uiState.collect { bleState ->
                _uiState.update {
                    ScanFragmentViewState(
                        bleState.scanState, bleState.deviceList,
                        bleState.selectedMacAddress, bleState.connectionState
                    )
                }
            }
        }
    }
}