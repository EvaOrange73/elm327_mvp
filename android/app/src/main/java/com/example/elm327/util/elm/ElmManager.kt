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
    private val TAG = "elm327"
    private val OUR_TAG = "OUR"
    private val READ_UUID = UUID.fromString("FFF1")
    private val WRITE_UUID = UUID.fromString("FFF2")  // TODO correct uuid init

    // ==== Required implementation ====
    // This is a reference to a characteristic that the manager will use internally.
    private var fluxCapacitorControlPoint: BluetoothGattCharacteristic? = null

    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean
    {
        // Here obtain instances of your characteristics.
        // Return false if a required service has not been discovered.
        Log.i(OUR_TAG, "isRequiredServiceSupported ENTERED!")
        val fluxCapacitorService = gatt.getService(WRITE_UUID)
        if (fluxCapacitorService != null)
        {
            fluxCapacitorControlPoint = fluxCapacitorService.getCharacteristic(WRITE_UUID)
        }
        return fluxCapacitorControlPoint != null
    }

    override fun initialize()
    {
        // Initialize your device.
        // This means e.g. enabling notifications, setting notification callbacks, or writing something to a Control Point characteristic.
        // Kotlin projects should not use suspend methods here, as this method does not suspend.
        Log.i(OUR_TAG, "initialize ENTERED!")
        setNotificationCallback(fluxCapacitorControlPoint).with { bluetoothDevice: BluetoothDevice, data: Data ->
            Log.i(bluetoothDevice.address, data.getStringValue(0)!!)
        }
        //enableNotifications(fluxCapacitorControlPoint)
        enableNotifications(fluxCapacitorControlPoint).enqueue()
        writeCharacteristic(fluxCapacitorControlPoint, Data.from("ATZ\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(fluxCapacitorControlPoint, Data.from("ATD\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(fluxCapacitorControlPoint, Data.from("ATH1\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(fluxCapacitorControlPoint, Data.from("ATS1\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
        writeCharacteristic(fluxCapacitorControlPoint, Data.from("ATSP6\r"), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
    }

    override fun onServicesInvalidated()
    {
        Log.i(OUR_TAG, "onServicesInvalidated ENTERED!")
        // This method is called when the services get invalidated, i.e. when the device disconnects.
        // References to characteristics should be nullified here.
        fluxCapacitorControlPoint = null
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
        writeCharacteristic(fluxCapacitorControlPoint, Data.from(pid.pid + '\r'), BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE).enqueue()
    }

}