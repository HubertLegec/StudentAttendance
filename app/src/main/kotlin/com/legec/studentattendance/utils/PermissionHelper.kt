package com.legec.studentattendance.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat


class PermissionHelper(private val activity: Activity) {
    val WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val WRITE_STORAGE_REQUEST_CODE = 98

    fun checkWriteStoragePermission(): Boolean {
        return checkPermission(WRITE_STORAGE_PERMISSION, WRITE_STORAGE_REQUEST_CODE, activity)
    }

    fun isWriteStoragePermissionGranted(grantResults: IntArray): Boolean {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(activity, WRITE_STORAGE_PERMISSION)
                    == PackageManager.PERMISSION_GRANTED) {
                return true
            }
        }
        return false
    }

    private fun checkPermission(permission: String, requestCode: Int, activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
                return false
            }
            return true
        }
        return true
    }
}