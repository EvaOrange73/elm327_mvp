package com.example.elm327.ui_layer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elm327.data_layer.BleRepository
import com.example.elm327.data_layer.SyncState
import com.example.elm327.data_layer.UnitOfMeasurement
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class CarInfoFragmentViewState(
    val carId: String? = null,
    val syncButtonState: SyncState = SyncState.NO_PERMISSIONS,
    val pidGetters: Map<ObdPids, DecodedPidValue> = mapOf(),
    val pidValues: Map<ObdPids, DecodedPidValue> = mapOf(),
    val unitOfMeasurement: UnitOfMeasurement = UnitOfMeasurement.METRIC_OPTIMAL,
)

class CarInfoFragmentViewModel(private val bleRepository: BleRepository) : ViewModel() {
    val LOG_TAG = "Car Info View Model"

    private val _uiState = MutableStateFlow(CarInfoFragmentViewState())
    val uiState = _uiState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val bleRepository: BleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CarInfoFragmentViewModel(bleRepository) as T
        }
    }

    init {
        viewModelScope.launch {
            bleRepository.uiState.collect { bleState ->
                _uiState.update {
                    CarInfoFragmentViewState(
                        bleState.carId,
                        bleState.syncState,
                        bleState.pidValues.filter { it.key in ObdPids.getters },
                        bleState.pidValues.filter { it.key in ObdPids.carInfo },
                        bleState.unitOfMeasurement,
                    )
                }
            }
        }
    }
}