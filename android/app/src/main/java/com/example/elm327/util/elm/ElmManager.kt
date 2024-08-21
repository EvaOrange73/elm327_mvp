package com.example.elm327.util.elm

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import no.nordicsemi.android.ble.BleManager
import no.nordicsemi.android.ble.data.Data
import java.util.UUID


class ElmManager(context: Context): BleManager(context)
{
    private val TAG = "MANAGER"
    private val OUR_TAG = "OUR"

    private val SERVICE_UUID = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb")
    private val READ_UUID = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb")
    private val WRITE_UUID = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb")  // TODO correct uuid init

    // ==== Required implementation ====
    // This is a reference to a characteristic that the manager will use internally.
    private var readCharacteristic: BluetoothGattCharacteristic? = null
    private var writeCharacteristic: BluetoothGattCharacteristic? = null

    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean
    {
        // Here obtain instances of your characteristics.
        // Return false if a required service has not been discovered.
        Log.i(OUR_TAG, "isRequiredServiceSupported ENTERED!")
        val fluxCapacitorService = gatt.getService(SERVICE_UUID)
        if (fluxCapacitorService != null)
        {
            readCharacteristic = fluxCapacitorService.getCharacteristic(READ_UUID)
            writeCharacteristic = fluxCapacitorService.getCharacteristic(WRITE_UUID)
        }
        return (writeCharacteristic != null && readCharacteristic != null)
    }

    override fun initialize()
    {
        // Initialize your device.
        // This means e.g. enabling notifications, setting notification callbacks, or writing something to a Control Point characteristic.
        // Kotlin projects should not use suspend methods here, as this method does not suspend.
        Log.i(OUR_TAG, "initialize ENTERED!")
        setNotificationCallback(readCharacteristic).with { bluetoothDevice: BluetoothDevice, data: Data ->
            val value = data.getStringValue(0)!!
            Log.i(OUR_TAG, value)
            Log.i(OUR_TAG, ObdPids.parse(value).second.toString())
        }
        beginAtomicRequestQueue().add(enableNotifications(readCharacteristic)
            .fail { _: BluetoothDevice, status: Int ->
                    log(Log.ERROR, "Could not subscribe: $status")
                    disconnect().enqueue() })
            .done { log(Log.INFO, "Target initialized") }
            .enqueue()
        //enableNotifications(fluxCapacitorControlPoint).enqueue()
        writeCharacteristic(writeCharacteristic, Data.from("ATZ\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(writeCharacteristic, Data.from("ATD\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(writeCharacteristic, Data.from("ATH1\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(writeCharacteristic, Data.from("ATS1\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(writeCharacteristic, Data.from("ATSP6\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
    }

    override fun onServicesInvalidated()
    {
        Log.i(OUR_TAG, "onServicesInvalidated ENTERED!")
        // This method is called when the services get invalidated, i.e. when the device disconnects.
        // References to characteristics should be nullified here.
        readCharacteristic = null
        writeCharacteristic = null
    }

    // ==== Logging =====
    override fun getMinLogPriority(): Int
    {
        // Use to return minimal desired logging priority.
        return Log.VERBOSE
    }

    override fun log(priority: Int, message: String)
    {
        // Log from here.
        Log.i(TAG, message)
    }

    // ==== Public API ====
    // Here you may add some high level methods for your device:
    fun readPid(pid: ObdPids)
    {
        Log.i(OUR_TAG, "readPid ENTERED!")
        writeCharacteristic(writeCharacteristic, Data.from("01" + pid.pid + '\r'), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
    }

}