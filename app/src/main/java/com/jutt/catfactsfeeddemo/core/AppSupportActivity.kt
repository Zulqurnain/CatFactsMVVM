package com.jutt.catfactsfeeddemo.core

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.jutt.catfactsfeeddemo.R
import com.jutt.catfactsfeeddemo.databinding.LayoutToolbarBinding
import com.jutt.catfactsfeeddemo.extensions.setupActionBar
import com.jutt.catfactsfeeddemo.helper.InternetConnectivityListener
import com.jutt.catfactsfeeddemo.utils.SnackBarAnchorViewInterface
import com.jutt.catfactsfeeddemo.utils.SnackBarInterface
import com.jutt.catfactsfeeddemo.utils.SnackBarViewHandler

abstract class AppSupportActivity : AppCompatActivity(),
    SnackBarInterface, SnackBarAnchorViewInterface by SnackBarAnchorViewInterface.RootViewSnackBar,
    SnackBarViewHandler by SnackBarViewHandler.NoInternetSnackBar {

    private var toolbarBinding: LayoutToolbarBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerConnectivityListener()

    }

    override fun setTitle(title: CharSequence?) {
        super.setTitle(title)
        toolbarBinding?.toolbarTitle?.text = title
    }

    override fun setTitle(titleId: Int) {
        super.setTitle(titleId)
        toolbarBinding?.toolbarTitle?.text = getString(titleId)
    }

    private fun registerConnectivityListener() {
        InternetConnectivityListener(applicationContext).apply {
            lifecycle.addObserver(this)
        }.networkStateObserver.observe(this, { isConnected ->
            if (!isConnected) {
                showSnackbar(this)
            } else {
                dismissSnackbar()
            }
        })
    }

    fun getActivity(): AppCompatActivity {
        return this
    }

    fun getContext(): Context {
        return this
    }

    protected fun setUpToolBar(
        toolbarBinding: LayoutToolbarBinding,
        showDefaultTitle: Boolean = false,
        showBackArrowOrHome: Boolean = true,
        @StringRes titleRes: Int = R.string.empty
    ) {
        this.toolbarBinding = toolbarBinding
        setupActionBar(toolbarBinding.toolbar) {
            setDisplayShowTitleEnabled(showDefaultTitle)
            setDisplayHomeAsUpEnabled(showBackArrowOrHome)
        }.apply {
            setTitle(titleRes)
        }
    }
}