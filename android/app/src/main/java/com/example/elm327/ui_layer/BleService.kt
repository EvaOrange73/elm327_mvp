package com.example.elm327.ui_layer

import android.Manifest
import android.annotation.SuppressLint
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
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.MacAddress

class BleService : Service() {
    private val LOG_TAG = "BleService"

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner: BluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private val handler: Handler = Handler()

    private val SCAN_PERIOD: Long = 3000


    private val devices: MutableList<Device> = mutableListOf()

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }


    override fun onBind(intent: Intent?): IBinder {
        return BleBinder()
    }

    inner class BleBinder : Binder() {

        val startScan: (() -> Unit) = {
            bleRepository.startScan()
            scanLeDevice()
        }

        val stopScan: (() -> Unit) = {
            bleRepository.stopScan()
            stopLeScan()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Ble Service", "started")
        bleRepository.setReadyToScan()
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
                if (!devices.contains(curDevice)) {
                    devices.add(curDevice)
                    bleRepository.updateDevices(devices)
                }
            }
        }
    }

    fun scanLeDevice() {
        handler.postDelayed({
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
            bleRepository.setScanningResult(devices)
        }, SCAN_PERIOD)
        bluetoothLeScanner.startScan(leScanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopLeScan() {
        bluetoothLeScanner.stopScan(leScanCallback)
    }
}


