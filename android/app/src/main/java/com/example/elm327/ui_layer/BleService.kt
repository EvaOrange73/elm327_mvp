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
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.elm.ElmManager

class BleService : Service() {
    private val LOG_TAG = "BleService"

    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner: BluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private val handler: Handler = Handler()

    private val SCAN_PERIOD: Long = 3000

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val elmManager: ElmManager by lazy {
        ElmManager(applicationContext)
    }

    private var devices: DeviceList = DeviceList()
    private var selectedMacAddress = MacAddress.getDefault()


    override fun onBind(intent: Intent?): IBinder {
        return BleBinder()
    }

    inner class BleBinder : Binder() {

        val startScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.SCANNING)
            scanLeDevice()
        }

        val stopScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.READY_TO_SCAN)
            stopLeScan()
        }

        val selectMacAddress: ((MacAddress) -> Unit) = { macAddress ->
            bleRepository.updateSelectedMacAddress(macAddress)
            selectedMacAddress = macAddress
        }

        val connect: (() -> Unit) = {
            bleRepository.updateConnectionState(ConnectionState.CONNECTING)
            val device = bluetoothAdapter.getRemoteDevice(selectedMacAddress.toString())
            elmManager.connect(device).useAutoConnect(true).enqueue()
        }

        val disconnect: (() -> Unit) = {
            // TODO
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Ble Service", "started")
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
        bleRepository.updateConnectionState(ConnectionState.READY_TO_CONNECT)
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
                if (devices.findDeviceByMacAddress(curAddress) == null) {
                    devices = devices.copyAndAdd(Device(curAddress)) //TODO сохранять имя
                    bleRepository.updateDeviceList(devices)
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
            Log.i(LOG_TAG, "Devices: $devices")
            bleRepository.updateDeviceList(devices)
        }, SCAN_PERIOD)
        bluetoothLeScanner.startScan(leScanCallback)
    }

    @SuppressLint("MissingPermission")
    fun stopLeScan() {
        bluetoothLeScanner.stopScan(leScanCallback)
    }
}


