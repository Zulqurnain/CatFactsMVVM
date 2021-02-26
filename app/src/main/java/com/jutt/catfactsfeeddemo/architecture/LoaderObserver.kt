package com.jutt.catfactsfeeddemo.architecture

import android.app.Activity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.lifecycle.Observer

class LoaderObserver(private val progressView: View) : Observer<Boolean> {
    override fun onChanged(loading: Boolean?) {
        progressView.isVisible = (loading == true)
        val window: Window = (progressView.context as? Activity)?.window ?: return
        if (loading == true) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }
}
