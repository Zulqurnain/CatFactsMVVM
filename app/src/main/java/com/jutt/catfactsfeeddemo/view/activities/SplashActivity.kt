package com.jutt.catfactsfeeddemo.view.activities

import android.view.LayoutInflater
import com.jutt.catfactsfeeddemo.core.BaseVVMActivity
import com.jutt.catfactsfeeddemo.databinding.ActivitySplashBinding
import com.jutt.catfactsfeeddemo.viewmodels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

class SplashActivity : BaseVVMActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getViewModelClass() = SplashViewModel::class.java

    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding
        get() = ActivitySplashBinding::inflate

    override fun onReady() {

    }
}