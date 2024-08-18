package com.example.elm327.util.elm

import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.content.Context
import android.util.Log
import no.nordicsemi.android.ble.BleManager


class ElmManager(context: Context) : BleManager(context) {
    private val TAG = "elm327"

    // ==== Logging =====
    @Override
    override fun getMinLogPriority(): Int {
        return Log.VERBOSE
    }

    @Override
    override fun log(priority: Int, message: String) {
        super.log(priority, message)
    }


    // ==== Required implementation ====
    // This is a reference to a characteristic that the manager will use internally.
    private var fluxCapacitorControlPoint: BluetoothGattCharacteristic? = null

//    override fun isRequiredServiceSupported(gatt: BluetoothGatt): Boolean {
//        // Here obtain instances of your characteristics.
//        // Return false if a required service has not been discovered.
//        val fluxCapacitorService = gatt.getService(FLUX_SERVICE_UUID)
//        if (fluxCapacitorService != null) {
//            fluxCapacitorControlPoint = fluxCapacitorService.getCharacteristic(FLUX_CHAR_UUID)
//        }
//        return fluxCapacitorControlPoint != null
//    }

    override fun initialize() {
        // Initialize your device.
        // This means e.g. enabling notifications, setting notification callbacks, or writing
        // something to a Control Point characteristic.
        // Kotlin projects should not use suspend methods here, as this method does not suspend.
        requestMtu(517)
            .enqueue()


//        await send("ATZ\r", sleep=1)
//        await send("ATD\r")
//        await send("ATH1\r")
//        await send("ATS1\r")
//        await send("ATSP6\r")
    }

    override fun onServicesInvalidated() {
        // This method is called when the services get invalidated, i.e. when the device
        // disconnects.
        // References to characteristics should be nullified here.
        fluxCapacitorControlPoint = null
        //TODO: try to reconnect
    }


    // ==== Public API ====
    // Here you may add some high level methods for your device:
    fun readPid(pid: String) {
//        writeCharacteristic(
//            fluxCapacitorControlPoint,
//            Flux.enable(),
//            BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
//        )
//            .enqueue()
    }
    
}