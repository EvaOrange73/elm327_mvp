package com.example.elm327.ui_layer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.elm327.data_layer.BleRepository
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MoreInfoFragmentViewState(
    val pidValues: Map<ObdPids, List<Value>> = mutableMapOf(),
)

class MoreInfoFragmentViewModel(private val bleRepository: BleRepository) : ViewModel(){
    val LOG_TAG = "More Info View Model"

    private val _uiState = MutableStateFlow(MoreInfoFragmentViewState())
    val uiState = _uiState.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    class Factory(private val bleRepository: BleRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoreInfoFragmentViewModel(bleRepository) as T
        }
    }

    init {
        viewModelScope.launch {
            bleRepository.uiState.collect { bleState ->
                _uiState.update {
                    MoreInfoFragmentViewState(
                        bleState.pidValues
                    )
                }
            }
        }
    }
}