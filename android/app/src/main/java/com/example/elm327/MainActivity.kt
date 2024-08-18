package com.example.elm327

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elm327.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val permissions: Permissions = Permissions(this@MainActivity)
    private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
    private val locationManager: LocationManager by lazy {
        applicationContext.getSystemService(
            LOCATION_SERVICE
        ) as LocationManager
    }

    var bound = false;
    var bleService: BleService? = null

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
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow),
            drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        permissions.showPermissionsState(
//            Manifest.permission.BLUETOOTH,
//            Manifest.permission.BLUETOOTH_ADMIN,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_BACKGROUND_LOCATION
//        )


        enableSystemService(bluetoothAdapter.isEnabled, BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableSystemService(locationManager.isLocationEnabled, Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun checkServices() {
        if (!bound && bluetoothAdapter.isEnabled && locationManager.isLocationEnabled) {
            val bleServiceIntent = Intent(this, BleService::class.java)
            startService(bleServiceIntent)
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, binder: IBinder) {
                    Log.d(LOG_TAG, "MainActivity onServiceConnected")
                    bleService = (binder as BleService.BleBinder).service
                    bound = true
                }

                override fun onServiceDisconnected(name: ComponentName) {
                    Log.d(LOG_TAG, "MainActivity onServiceDisconnected")
                    bound = false
                }
            }
            bindService(bleServiceIntent, serviceConnection, 0)
        }
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


    fun startScan() {
        Log.i(LOG_TAG, "start scan")
        if (bound) {
            bleService!!.scanLeDevice()
        }
    }
}

