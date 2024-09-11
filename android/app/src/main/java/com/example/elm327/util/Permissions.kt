package com.example.elm327.util

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.content.pm.ServiceInfo
import android.os.Build
import android.text.BoringLayout
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.elm327.ui_layer.MainActivity
import com.example.elm327.util.value.Bool

class Permissions(private val mainActivity: MainActivity) {
    private val LOG_TAG = "Permissions"

    private var curPermission: String? = null
    private val REQUEST_PERMISSION_BLE: Int = 1

    companion object
    {
        val permissionsToCheck: List<String> = listOf(
                                Manifest.permission.BLUETOOTH,
                                Manifest.permission.BLUETOOTH_ADMIN,
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                                     )
        var simplePermissionsAsk: Array<String> = arrayOf()

        val foregroundPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                                            {
                                                ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE or ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
                                            }
                                            else
                                            {
                                                0
                                            }
    }

    fun showPermissionsState() : Boolean {
        for (permission in permissionsToCheck) {
            curPermission = permission
            showCurPermissionState()
        }
        if (simplePermissionsAsk.isNotEmpty())
        {
            ActivityCompat.requestPermissions(this.mainActivity, simplePermissionsAsk, REQUEST_PERMISSION_BLE)
            return false
        }
        return true
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) : Boolean {
        if (requestCode == REQUEST_PERMISSION_BLE) {
             if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                for (permission in permissions)
                {
                    if (permission != null) Log.i(LOG_TAG, "Permission ${permission.split('.').last()} Granted!")
                }
            }
            else {
                for (permission in permissions)
                {
                    if (permission != null) Log.i(LOG_TAG, "Permission ${permission.split('.').last()} Denied!")
                }
            }
        }
        for (permission in permissionsToCheck) {
            if (ContextCompat.checkSelfPermission(this.mainActivity, permission) != PackageManager.PERMISSION_GRANTED) return false
        }
        return true
    }

    private fun showCurPermissionState() {
        if (curPermission == null) return
        val permissionCheck = ContextCompat.checkSelfPermission(this.mainActivity, curPermission!!)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity, curPermission!!)) {
                showExplanation("Permission Needed", "Rationale", curPermission!!, REQUEST_PERMISSION_BLE)
                //simplePermissionsAsk += arrayOf(curPermission!!)
            }
            else {
                simplePermissionsAsk += arrayOf(curPermission!!)
            }
        }
        else {
            Log.i(LOG_TAG, "Permission ${curPermission!!.split('.').last()} already Granted!")
        }
    }

    private fun showExplanation(title: String, message: String, permission: String, permissionRequestCode: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this.mainActivity)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, id -> requestPermission(permission, permissionRequestCode) })
        builder.create().show()
    }

    private fun requestPermission(permissionName: String, permissionRequestCode: Int) {
        ActivityCompat.requestPermissions(this.mainActivity, arrayOf(permissionName), permissionRequestCode)
    }
}