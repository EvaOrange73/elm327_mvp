package com.example.elm327.util.elm

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ConnectionState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.data.Data
import java.sql.Timestamp
import java.util.UUID


class ElmManager(context: Context) : BleManager(context) {
    private val TAG = "MANAGER"
    private val OUR_TAG = "OUR"

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
    private val READ_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
    private val WRITE_UUID =
        UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb")  // TODO correct uuid init

    private val initCommands = listOf("ATZ", "ATD", "ATH1", "ATS1", "ATSP6") // синий
    // TODO красный

    // ==== Required implementation ====
    private var readCharacteristic: BluetoothGattCharacteristic? = null
    private var writeCharacteristic: BluetoothGattCharacteristic? = null

    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
        val fluxCapacitorService = gatt.getService(SERVICE_UUID)
        if (fluxCapacitorService != null) {
            readCharacteristic = fluxCapacitorService.getCharacteristic(READ_UUID)
            writeCharacteristic = fluxCapacitorService.getCharacteristic(WRITE_UUID)
        }
        return (writeCharacteristic != null && readCharacteristic != null)
    }

    override fun initialize() {
        setNotificationCallback(readCharacteristic).with { bluetoothDevice: BluetoothDevice, data: Data ->
            val value = data.getStringValue(0)!!
            Log.i(OUR_TAG, value)
            Log.i(OUR_TAG, ObdPids.parse(value, System.currentTimeMillis()).valuesAsString())
            bleRepository.updatePidValue(ObdPids.parse(value, System.currentTimeMillis()))
        }
        beginAtomicRequestQueue().add(enableNotifications(readCharacteristic)
            .fail { _: BluetoothDevice, status: Int ->
                log(Log.ERROR, "Could not subscribe: $status")
                disconnect().enqueue()
            })
            .done { log(Log.INFO, "Target initialized") }
            .enqueue()
        //enableNotifications(fluxCapacitorControlPoint).enqueue()

        initCommands.forEach {
            writeCharacteristic(
                writeCharacteristic,
                Data.from(it + "\r"),
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
            ).enqueue()
        }

        bleRepository.updateConnectionState(ConnectionState.CONNECTED)
    }

    override fun onServicesInvalidated() {
        readCharacteristic = null
        writeCharacteristic = null
        bleRepository.updateConnectionState(ConnectionState.FAIL)
    }

    // ==== Logging =====
    override fun getMinLogPriority(): Int {
        return Log.VERBOSE
    }

    override fun log(priority: Int, message: String) {
        Log.i(TAG, message)
    }

    // ==== Public API ====
    private suspend fun readPid(pid: ObdPids) {
        if (continueReading) {
            writeCharacteristic(
                writeCharacteristic,
                Data.from("01" + pid.pid + '\r'),
                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
            ).enqueue()
            Log.i(OUR_TAG, "start read ${pid.pid}")
            delay(1000)
        }
    }

    var selectedPid: ObdPids? = null
    var continueReading = false

    suspend fun startRead() {
        continueReading = true
        while (continueReading) {
            for (pid in ObdPids.entries) {
                if (selectedPid == null) {
                    GlobalScope.async { readPid(pid) }.await()
                } else while (selectedPid != null && continueReading) {
                    GlobalScope.async { readPid(selectedPid!!) }.await()
                }
            }
        }
    }

    fun stopRead(){
        continueReading = false
    }
}