package com.example.elm327.data_layer

import android.location.Location
import android.util.Log
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.DecodedPidValue
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

    fun updateLocation(location: Location){
        Log.i(LOG_TAG, "${location.longitude} ${location.latitude}")

        if (_uiState.value.carId != null && _uiState.value.location != null) {
            BleNetworkDataSource.updateLocation(
                _uiState.value.carId!!,
                _uiState.value.location!!,
            )
        }

        _uiState.update {
            it.copy(location = location)
        }
    }


    fun updateScanState(scanState: ScanState) {
        _uiState.update {
            it.copy(scanState = scanState)
        }
    }

    fun updateDeviceList(deviceList: DeviceList) {
        _uiState.update {
            it.copy(deviceList = deviceList)
        }
    }

    fun updateSelectedMacAddress(macAddress: MacAddress) {
        _uiState.update {
            it.copy(selectedMacAddress = macAddress)
        }
    }


    fun updateConnectionState(connectionState: ConnectionState) {
        _uiState.update {
            it.copy(connectionState = connectionState)
        }
    }

    fun updatePidValue(decodedPidValue: DecodedPidValue) {
        if (_uiState.value.carId != null && _uiState.value.location != null) {
            BleNetworkDataSource.updatePid(
                _uiState.value.carId!!,
                _uiState.value.location!!,
                decodedPidValue
            )
        }
        _uiState.update {
            it.copy(pidValues = sortedMapOf(*it.pidValues.toList().toTypedArray(), Pair(decodedPidValue.pid, decodedPidValue)))
        }
    }

    fun updateCarId(carId: String?) {
        if (carId == null) {
            _uiState.update {
                it.copy(carId = carId, syncState = SyncState.NOT_SYNCHRONIZED)
            }
        } else {
            _uiState.update {
                it.copy(carId = carId, syncState = SyncState.SYNCHRONIZED)
            }
        }
    }

    fun setNextUnitOfMeasurement(){
        _uiState.update {
            it.copy(unitOfMeasurement = it.unitOfMeasurement.next())
        }
    }
}