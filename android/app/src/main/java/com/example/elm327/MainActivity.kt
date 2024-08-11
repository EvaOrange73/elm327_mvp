package com.example.elm327

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elm327.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val REQUEST_PERMISSION_BLE = 1
    private val SCAN_PERIOD: Long = 10000

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private val bluetoothLeScanner = bluetoothAdapter.bluetoothLeScanner
    private val handler = Handler()

    private val locationManager by lazy {
        applicationContext.getSystemService(
            LOCATION_SERVICE
        ) as LocationManager
    }


    private var cur_permission: String? = null
    private var scanning = false

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

        this.showPermissionState(
            arrayOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        )

        enableSystemService(bluetoothAdapter.isEnabled, BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableSystemService(
            locationManager.isLocationEnabled,
            Settings.ACTION_LOCATION_SOURCE_SETTINGS
        )


    }


    private fun enableSystemService(isEnabled: Boolean, actionRequest: String) {
        if (!isEnabled) {
            val enableBtIntent = Intent(actionRequest)
            val enableLauncher = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { }
            enableLauncher.launch(enableBtIntent)
        }
    }


    fun onClick(view: View) {
        scanLeDevice()
    }

    private fun showPermissionState(permissions: Array<String>) {
        for (permission in permissions) {
            cur_permission = permission
            showPermissionState()
        }
    }

    private fun showPermissionState() {
        if (cur_permission == null) return
        val permissionCheck = ContextCompat.checkSelfPermission(this, cur_permission!!)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, cur_permission!!)) {
                showExplanation("Permission Needed", "Rationale", cur_permission!!, REQUEST_PERMISSION_BLE)
            }
            else {
                requestPermission(cur_permission!!, REQUEST_PERMISSION_BLE)
            }
        }
        else {
            Toast.makeText(this@MainActivity, "Permission ${cur_permission!!.split('.').last()} already Granted!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_PERMISSION_BLE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (cur_permission == null) {
                    Toast.makeText(this@MainActivity, "Permission ??? Granted!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@MainActivity, "Permission ${cur_permission!!.split('.').last()} Granted!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                if (cur_permission == null) {
                    Toast.makeText(this@MainActivity, "Permission ??? Denied!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this@MainActivity, "Permission ${cur_permission!!.split('.').last()} Denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showExplanation(title: String, message: String, permission: String, permissionRequestCode: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> requestPermission(permission, permissionRequestCode) })
        builder.create().show()
    }

    private fun requestPermission(permissionName: String, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(this, arrayOf(permissionName), permissionRequestCode)
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Toast.makeText(applicationContext, result.device.address, Toast.LENGTH_SHORT).show()
        }
    }

    private fun scanLeDevice() {
        if (!scanning) {
            handler.postDelayed({
                scanning = false
                if (Build.VERSION.SDK_INT >= 31 &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(applicationContext, "New Android!", Toast.LENGTH_SHORT).show()
                    // TODO: Consider calling ActivityCompat#requestPermissions
                }
                bluetoothLeScanner.stopScan(leScanCallback)
            }, SCAN_PERIOD)
            scanning = true
            Toast.makeText(applicationContext, Build.VERSION.SDK_INT.toString(), Toast.LENGTH_SHORT).show()
            bluetoothLeScanner.startScan(leScanCallback)
        }
        else {
            scanning = false
            bluetoothLeScanner.stopScan(leScanCallback)
        }
    }


}

