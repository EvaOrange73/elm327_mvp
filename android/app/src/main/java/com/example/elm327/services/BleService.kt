package com.example.elm327.services

import android.Manifest
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.elm327.data.Device
import com.example.elm327.data.MacAddress

class BleService : Service() {
    private val LOG_TAG = "BleService"

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner: BluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private val handler: Handler = Handler()

    private val SCAN_PERIOD: Long = 3000

    var scanning: Boolean = false

    private val devices: MutableList<Device> = mutableListOf()


    override fun onBind(intent: Intent?): IBinder {
        return BleBinder()
    }

    inner class BleBinder : Binder() {
        val service: BleService
            get() = this@BleService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Ble Service", "started")
        return super.onStartCommand(intent, flags, startId)
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if (Build.VERSION.SDK_INT >= 31 &&
                ActivityCompat.checkSelfPermission(
                    this@BleService,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "New Android!", Toast.LENGTH_SHORT).show()
                // TODO: Consider calling ActivityCompat#requestPermissions
            } else {
                val curAddress: MacAddress = MacAddress(result.device.address)
                val curDevice: Device = Device(curAddress, "")
                if (!devices.contains(curDevice)) devices.add(curDevice)
            }
        }
    }

    fun scanLeDevice() {
        if (!scanning) {
            handler.postDelayed({
                scanning = false
                if (Build.VERSION.SDK_INT >= 31 &&
                    ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_SCAN
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(applicationContext, "New Android!", Toast.LENGTH_SHORT).show()
                    // TODO: Consider calling ActivityCompat#requestPermissions
                }
                bluetoothLeScanner.stopScan(leScanCallback)
                Log.i(LOG_TAG, "Devices: " + devices.joinToString(", ") { it.toString() })
            }, SCAN_PERIOD)
            scanning = true
            bluetoothLeScanner.startScan(leScanCallback)
        } else {
            scanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}


