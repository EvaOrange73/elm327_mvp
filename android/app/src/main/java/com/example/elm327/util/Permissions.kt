package com.example.elm327.util

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.elm327.ui_layer.MainActivity

class Permissions(private val mainActivity: MainActivity) {
    private val LOG_TAG = "Permissions"

    private var curPermission: String? = null
    private val REQUEST_PERMISSION_BLE: Int = 1

    fun showPermissionsState(
        permissionsToCheck: List<String> = listOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.INTERNET,
    )) {
        for (permission in permissionsToCheck) {
            curPermission = permission
            showCurPermissionState()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_BLE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (curPermission == null) {
                    Log.i(LOG_TAG, "Permission ??? Granted!")
                }
                else {
                    Log.i(LOG_TAG, "Permission ${curPermission!!.split('.').last()} Granted!")
                }
            }
            else {
                if (curPermission == null) {
                    Log.i(LOG_TAG, "Permission ??? Denied!")
                }
                else {
                    Log.i(LOG_TAG, "Permission ${curPermission!!.split('.').last()} Denied!")
                }
            }
        }
    }

    private fun showCurPermissionState() {
        if (curPermission == null) return
        val permissionCheck = ContextCompat.checkSelfPermission(this.mainActivity, curPermission!!)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.mainActivity, curPermission!!)) {
                showExplanation("Permission Needed", "Rationale", curPermission!!, REQUEST_PERMISSION_BLE)
            }
            else {
                requestPermission(curPermission!!, REQUEST_PERMISSION_BLE)
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