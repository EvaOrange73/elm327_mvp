package com.example.elm327

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val REQUEST_PERMISSION_BLE = 1
    private var cur_permission: String? = null

    @RequiresApi(Build.VERSION_CODES.S)
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
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        this.showPermissionState(arrayOf(Manifest.permission.POST_NOTIFICATIONS,
                                         Manifest.permission.BLUETOOTH,
                                         Manifest.permission.BLUETOOTH_ADMIN,
                                         Manifest.permission.ACCESS_FINE_LOCATION,
                                         Manifest.permission.ACCESS_COARSE_LOCATION,
                                         Manifest.permission.ACCESS_BACKGROUND_LOCATION
                                         ))

    }

    fun onClick(view: View) {
        Toast.makeText(this@MainActivity, "Button clicked!", Toast.LENGTH_SHORT).show()
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
        } else {
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
}

