package com.example.elm327.ui.scan

import androidx.lifecycle.ViewModel
import com.example.elm327.BleService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


enum class ScanButtonState {
    NO_PERMISSIONS,
    READY_TO_SCAN,
    SCANNING
}

class ScanState(
    val scanButtonState: ScanButtonState = ScanButtonState.NO_PERMISSIONS,
    val spinnerList: List<String> = listOf()
)

class ScanViewModel() : ViewModel() {

    private val _uiState = MutableStateFlow(ScanState())
    val uiState: StateFlow<ScanState> = _uiState.asStateFlow()

    fun startScan() {
        _uiState.update { currentState ->
            ScanState(ScanButtonState.SCANNING, currentState.spinnerList)
        }
    }

    fun stopScan(){
        _uiState.update { currentState ->
            ScanState(ScanButtonState.READY_TO_SCAN, currentState.spinnerList)
        }
    }

    fun setScanningResult(spinnerArray: List<String>) {
        _uiState.update {
            ScanState(ScanButtonState.READY_TO_SCAN, spinnerArray)
        }
    }
}