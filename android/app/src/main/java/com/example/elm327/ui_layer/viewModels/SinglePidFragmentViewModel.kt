package com.example.elm327.ui_layer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elm327.data_layer.BleRepository
import com.example.elm327.data_layer.UnitOfMeasurement
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.elm.ObdPids
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SinglePidFragmentViewState(
    val values: List<DecodedPidValue> = listOf(),
    val unitOfMeasurement: UnitOfMeasurement = UnitOfMeasurement.METRIC_OPTIMAL,
)

class SinglePidFragmentViewModel(private val bleRepository: BleRepository) : ViewModel(){
    val LOG_TAG = "Single Pid View Model"

    private val _uiState = MutableStateFlow(SinglePidFragmentViewState())
    val uiState = _uiState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val bleRepository: BleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SinglePidFragmentViewModel(bleRepository) as T
        }
    }

    init {
        viewModelScope.launch {
            bleRepository.uiState.collect { bleState ->
                _uiState.update {
                    val newValues = _uiState.value.values.toMutableList()
                    bleState.pidValues[ObdPids.PID_0C]?.let { it1 -> newValues.add(it1) } //TODO
                    SinglePidFragmentViewState(
                        newValues,
                        bleState.unitOfMeasurement
                    )
                }
            }
        }
    }
}