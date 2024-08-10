package com.example.elm327

class BleScanActivity  {
//    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
//    private var scanning = false
//    private val handler = Handler()
//
//    private val leDeviceListAdapter = LeDeviceListAdapter()
//    // Device scan callback.
//    private val leScanCallback: ScanCallback = object : ScanCallback() {
//        override fun onScanResult(callbackType: Int, result: ScanResult) {
//            super.onScanResult(callbackType, result)
//            leDeviceListAdapter.addDevice(result.device)
//            leDeviceListAdapter.notifyDataSetChanged()
//        }
//    }
//
//    // Stops scanning after 10 seconds.
//    private val SCAN_PERIOD: Long = 10000
//
//    private fun scanLeDevice() {
//        if (!scanning) { // Stops scanning after a pre-defined scan period.
//            handler.postDelayed({
//                scanning = false
//                bluetoothLeScanner.stopScan(leScanCallback)
//            }, SCAN_PERIOD)
//            scanning = true
//            bluetoothLeScanner.startScan(leScanCallback)
//        } else {
//            scanning = false
//            bluetoothLeScanner.stopScan(leScanCallback)
//        }
//    }
}