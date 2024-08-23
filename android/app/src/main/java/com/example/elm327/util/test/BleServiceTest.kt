package com.example.elm327.util.test

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.example.elm327.data_layer.BleRepositoryImp
import com.example.elm327.data_layer.ConnectionState
import com.example.elm327.data_layer.ScanState
import com.example.elm327.data_layer.model.Device
import com.example.elm327.data_layer.model.MacAddress
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File

class BleServiceTest : Service() {

    private val bleRepository: BleRepositoryImp by lazy {
        BleRepositoryImp.getInstance()
    }

    private val fakeDeviceList = listOf(
        Device(MacAddress("mac address 1"), ""),
        Device(MacAddress("mac address 2"), ""),
        Device(MacAddress("mac address 3"), ""),
        Device(MacAddress("mac address 4"), ""),
    )

    override fun onBind(intent: Intent?): IBinder {
        return BleBinderTest()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("Ble Service Test", "started")
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
        bleRepository.updateConnectionState(ConnectionState.READY_TO_CONNECT)
        return super.onStartCommand(intent, flags, startId)
    }

    inner class BleBinderTest : Binder() {

        @OptIn(DelicateCoroutinesApi::class)
        val startScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.SCANNING)
            GlobalScope.async {fakeScan()}
        }

        val stopScan: (() -> Unit) = {
            bleRepository.updateScanState(ScanState.READY_TO_SCAN)
            //TODO
        }

        val selectMacAddress: ((MacAddress) -> Unit) = { macAddress ->
            bleRepository.updateSelectedMacAddress(macAddress)
        }

        val connect: (() -> Unit) = {
            bleRepository.updateConnectionState(ConnectionState.CONNECTING)
            generateFakePidValues()
        }

        val disconnect: (() -> Unit) = {
            // TODO
        }
    }


    suspend fun fakeScan()  {
        val devices: MutableList<Device> = mutableListOf()
        fakeDeviceList.forEach {
            delay(1000)
            devices.add(it)
            bleRepository.updateDeviceList(devices)
        }
        bleRepository.updateScanState(ScanState.READY_TO_SCAN)
    }

    fun generateFakePidValues() {
        val file = File("recorded_pid_values.txt")
        file.readLines().forEach {
            Handler().postDelayed({
                //TODO
            }, 300)
        }
    }


}