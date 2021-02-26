package com.jutt.catfactsfeeddemo.core

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
abstract class AppSupportFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    @StringRes
    open val titleResId: Int? = null
    open val name: String by lazy { this.javaClass.simpleName }

    open fun getTitle(): String? = titleResId?.let { getString(it) }

    var backStackTransactionName: String? = null

    /**
     * Called by Activity whenever back button is invoked. The Fragment should perform the
     * desired operation (if required) and return true if Activity can continue its execution;
     * false if Fragment desires to block user here
     *
     * @return false if there is input required from user or desires to block; true otherwise
     */
    open fun onNavigateBack(): Boolean = true

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE -> {
            }
            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }
}
