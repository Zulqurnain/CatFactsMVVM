package com.jutt.catfactsfeeddemo.utils

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.jutt.catfactsfeeddemo.R

interface SnackBarInterface : SnackBarAnchorViewInterface, SnackBarViewHandler {
    fun showSnackbar(activity: Activity) {
        getAnchorView(activity)?.let { rootView ->
            showSnackbar(rootView)
        }
    }
}

interface SnackBarViewHandler {
    fun showSnackbar(rootView: View)

    fun dismissSnackbar()

    object NoInternetSnackBar : SnackBarViewHandler {

        private var snackbar: Snackbar? = null

        override fun showSnackbar(rootView: View) {
            snackbar?.dismiss()
            snackbar = createSnackbar(rootView).apply {
                show()
            }
        }

        override fun dismissSnackbar() {
            snackbar?.dismiss()
        }

        private fun createSnackbar(rootView: View) =
            Snackbar.make(
                rootView,
                R.string.error_title_network_not_available,
                Snackbar.LENGTH_INDEFINITE
            ).apply {
                setAction(R.string.title_settings) {
                    it.context.startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
                }
            }
    }
}

interface SnackBarAnchorViewInterface {
    fun getAnchorView(activity: Activity): View?

    object RootViewSnackBar : SnackBarAnchorViewInterface {
        override fun getAnchorView(activity: Activity): View? {
            val contentViewGroup = activity.findViewById<View>(android.R.id.content) as? ViewGroup
            return contentViewGroup?.getChildAt(0) ?: activity.window.decorView.rootView
        }
    }
}



