package com.example.elm327.util

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.elm327.ui_layer.MainActivity

class Permissions(private val mainActivity: MainActivity) {
    private var curPermission: String? = null
    private val REQUEST_PERMISSION_BLE: Int = 1

    fun showPermissionsState(vararg permissionsToCheck: String) {
        for (permission in permissionsToCheck) {
            curPermission = permission
            showCurPermissionState()
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_BLE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (curPermission == null) {
                    Toast.makeText(this.mainActivity, "Permission ??? Granted!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this.mainActivity, "Permission ${curPermission!!.split('.').last()} Granted!", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                if (curPermission == null) {
                    Toast.makeText(this.mainActivity, "Permission ??? Denied!", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(this.mainActivity, "Permission ${curPermission!!.split('.').last()} Denied!", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this.mainActivity, "Permission ${curPermission!!.split('.').last()} already Granted!", Toast.LENGTH_SHORT).show()
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