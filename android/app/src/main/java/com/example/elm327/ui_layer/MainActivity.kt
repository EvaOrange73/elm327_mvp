package com.example.elm327.ui_layer

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.elm327.R
import com.example.elm327.data_layer.model.MacAddress
import com.example.elm327.databinding.ActivityMainBinding
import com.example.elm327.util.Permissions
import com.example.elm327.util.test.BleServiceTest
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


@RequiresApi(Build.VERSION_CODES.P)
class MainActivity : AppCompatActivity() {
    private val LOG_TAG = "MainActivity"

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val permissions: Permissions = Permissions(this@MainActivity)
    private val bluetoothAdapter: BluetoothAdapter by lazy { BluetoothAdapter.getDefaultAdapter() }
    private val locationManager: LocationManager by lazy { applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager }
    private val sharedPreferences: String = "preferenceName"

    var bound = false

    //    lateinit var bleBinder : BleService.BleBinder
    var bleBinder: BleServiceTest.BleBinderTest? = null
        get() = field.also { bindBleService() }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ShowToast", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
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

        val res = getPreferenceValue(MacAddress.preferenceKey)
        if (res != null && res != "")
        {
            MacAddress.setDefault(res)
        }

        permissions.showPermissionsState()

        checkSystemServicesEnabled()
    }

    private fun checkSystemServicesEnabled() {
        enableSystemService(bluetoothAdapter.isEnabled, BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableSystemService(
            locationManager.isLocationEnabled,
            Settings.ACTION_LOCATION_SOURCE_SETTINGS
        )
    }

    private fun enableSystemService(isEnabled: Boolean, actionRequest: String) {
        if (!isEnabled) {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { bindBleService() }
                .launch(Intent(actionRequest))
        }
    }

    private fun bindBleService() {
        if (!bound && bluetoothAdapter.isEnabled && locationManager.isLocationEnabled) {
//            val bleServiceIntent = Intent(this, BleService::class.java)
            val bleServiceIntent = Intent(this, BleServiceTest::class.java)
            startService(bleServiceIntent)
            val serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName, binder: IBinder) {
//                    bleBinder = binder as BleService.BleBinder
                    bleBinder = binder as BleServiceTest.BleBinderTest
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.permissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun getPreferenceValue(key: String): String?
    {
        return getSharedPreferences(sharedPreferences, 0).getString(key, "")
    }

    fun writeToPreference(key: String, value: String?)
    {
        val editor = getSharedPreferences(sharedPreferences, 0).edit()
        editor.putString(key, value)
        editor.apply()
    }
}

