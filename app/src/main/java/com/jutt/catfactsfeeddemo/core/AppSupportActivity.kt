package com.jutt.catfactsfeeddemo.core

import android.content.Context
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
abstract class AppSupportActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks{

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun getActivity(): AppCompatActivity {
        return this
    }

    fun getContext(): Context {
        return this
    }

    override fun onPermissionsGranted(requestCode: Int, @NonNull perms: List<String>) {
        Timber.i("Following permissions have been granted: $perms")
    }

    override fun onPermissionsDenied(requestCode: Int, @NonNull perms: List<String>) {
        Timber.i("Following permissions have been denied: $perms")
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}