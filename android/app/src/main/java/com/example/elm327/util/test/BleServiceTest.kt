package com.example.elm327.util.test

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.elm327.R
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.DeviceList
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.ui_layer.MainActivity
import com.example.elm327.util.RawResourcesReader
import com.example.elm327.util.elm.ObdPids
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay


class BleServiceTest : Service() {
    private val LOG_TAG = "Ble Service Test"

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val fakeDeviceList = listOf(
        Device(MacAddress("mac address 1"), ""),
        Device(MacAddress("mac address 2"), ""),
        Device(MacAddress("mac address 3"), ""),
        Device(MacAddress("mac address 4"), ""),
    )

    private var fakeConnection = false

    override fun onBind(intent: Intent?): IBinder {
        return BleBinderTest()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(LOG_TAG, "started")
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
        bleRepository.updateConnectionState(ConnectionState.READY_TO_CONNECT)
        return super.onStartCommand(intent, flags, startId)
    }

    inner class BleBinderTest : Binder() {

        @OptIn(DelicateCoroutinesApi::class)
        val startScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.SCANNING)
            GlobalScope.async { fakeScan() }
        }

        val stopScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.READY_TO_SCAN)
            //TODO
        }

        @RequiresApi(Build.VERSION_CODES.P)
        fun selectMacAddress(macAddress: MacAddress, activity: MainActivity) {
            bleRepository.updateSelectedMacAddress(macAddress)
            activity.writeToPreference(MacAddress.preferenceKey, macAddress.toString())
        }

        @OptIn(DelicateCoroutinesApi::class)
        val connect: (() -> Unit) = {
            bleRepository.updateConnectionState(ConnectionState.CONNECTING)
            fakeConnection = true
            GlobalScope.async { generateFakePidValues() }
        }

        val disconnect: (() -> Unit) = {
            fakeConnection = false
        }
    }


    suspend fun fakeScan() {
        val devices = DeviceList()
        fakeDeviceList.forEach {
            delay(1000)
            devices.add(it)
            bleRepository.updateDeviceList(devices)
        }
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
    }


    suspend fun generateFakePidValues() {
        val lines = RawResourcesReader.readLines(applicationContext, R.raw.recorded_pid_values)
        Log.i(LOG_TAG, lines[0])

        bleRepository.updateConnectionState(ConnectionState.CONNECTED)

        while (fakeConnection) {
            var lastTime = lines[0].split(" ")[0].toLong()
            lines.forEach {
                if (fakeConnection) {
                    val time = it.split(" ")[0].toLong()
                    val del = (time - lastTime) / 1_000_000
                    delay(del)
                    lastTime = time

                    val data = it.split(" ")[1]

                    val (pid, value) = ObdPids.parse(data)
                    bleRepository.updatePidValue(pid, value)
//                    Log.i(LOG_TAG, "pid ${pid.pid} updated with value $value after $del milliseconds")
                }
            }
        }

        bleRepository.updateConnectionState(ConnectionState.FAIL)
    }


}