package com.example.elm327.ui_layer

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.example.elm327.data_layer.BleNetworkDataSource
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.util.DecodedPidValue
import com.example.elm327.util.Permissions
import com.example.elm327.util.elm.ElmManager
import com.example.elm327.util.elm.ObdPids
import com.example.elm327.util.value.RawData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BleService : Service() {
    private val LOG_TAG = "BleService"
    private val NOTIFICATION_NAME = "ELM327"
    private val NOTIFICATION_ID = 52

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
            Log.i(LOG_TAG, "connect")
            bleRepository.updateConnectionState(ConnectionState.CONNECTING)
            val device = bluetoothAdapter.getRemoteDevice(selectedMacAddress.toString())
            elmManager.connect(device).useAutoConnect(true).enqueue()
            GlobalScope.async { elmManager.startRead() }
        }

        val selectPid: ((ObdPids) -> Unit) = { pid ->
            elmManager.selectedPid = pid
        }

        val selectAll: (() -> Unit) = {
            elmManager.selectedPid = null
        }

        val disconnect: (() -> Unit) = {
            elmManager.stopRead()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOG_TAG, "started")
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
        bleRepository.updateConnectionState(ConnectionState.READY_TO_CONNECT)
        val channel = NotificationChannel(NOTIFICATION_NAME, NOTIFICATION_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_NAME).build()
        ServiceCompat.startForeground(this, NOTIFICATION_ID, notification, Permissions.foregroundPermissions)
//        GlobalScope.launch(Dispatchers.IO) {
//            while (true) {
//                launch(Dispatchers.IO) {
//                    Log.i(LOG_TAG, "hui")
//                    if (bleRepository.uiState.value.location != null)
//                    {
//                        BleNetworkDataSource.updatePid("1", bleRepository.uiState.value.location!!, DecodedPidValue(System.currentTimeMillis(), "0", ObdPids.PID_01, listOf(RawData.raw("0"))))
//                    }
//                }
//                delay(1000)
//            }
//            Log.i(LOG_TAG, "End of the loop for the service")
//        }
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


