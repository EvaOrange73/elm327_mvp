package com.example.elm327

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elm327.databinding.ActivityMainBinding
import com.example.elm327.elm.MacAddress
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val SCAN_PERIOD: Long = 3000

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val permissions: Permissions = Permissions(this@MainActivity)
    private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
    private val bluetoothLeScanner: BluetoothLeScanner by lazy{ bluetoothAdapter.bluetoothLeScanner }
    private val locationManager: LocationManager by lazy { applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager }
    private val handler: Handler = Handler()

    private var scanning: State<Boolean> = State<Boolean>(false)
    private var devices: MutableList<Device> = mutableListOf<Device>()

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ShowToast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        permissions.showPermissionsState(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        scanning.bindOnSet { newValue: Boolean ->
            if (newValue) {
                findViewById<Button>(R.id.button).setBackgroundColor(Color.RED)
            }
            else {
                findViewById<Button>(R.id.button).setBackgroundColor(Color.GREEN)
            }
        }

        enableSystemService(bluetoothAdapter.isEnabled, BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableSystemService(locationManager.isLocationEnabled, Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkServices(): Boolean {
        if (bluetoothAdapter.isEnabled && locationManager.isLocationEnabled) {
            scanning.setValue(false)
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun enableSystemService(isEnabled: Boolean, actionRequest: String) {
        if (!isEnabled) {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { checkServices() }
                .launch(Intent(actionRequest))
        }
        checkServices()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.permissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            if (Build.VERSION.SDK_INT >= 31 &&
                ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(applicationContext, "New Android!", Toast.LENGTH_SHORT).show()
                // TODO: Consider calling ActivityCompat#requestPermissions
            }
            else {
                val curAddress: MacAddress = MacAddress(result.device.address)
                val curDevice: Device = Device(curAddress, "")
                if (!devices.contains(curDevice)) {
                    devices.add(curDevice)
                }
            }
        }
    }

    fun onClick(view: View) {
        if (!scanning.getValue()) {
            handler.postDelayed({
                scanning.setValue(false)
                if (Build.VERSION.SDK_INT >= 31 &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "New Android!", Toast.LENGTH_SHORT).show()
                    // TODO: Consider calling ActivityCompat#requestPermissions
                }
                bluetoothLeScanner.stopScan(leScanCallback)
                Toast.makeText(applicationContext, "Devices:\n" + devices.joinToString("\n") { it.toString() }, Toast.LENGTH_LONG).show()
            }, SCAN_PERIOD)
            scanning.setValue(true)
            bluetoothLeScanner.startScan(leScanCallback)
        }
        else {
            scanning.setValue(false)
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }
}

