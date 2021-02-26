package com.jutt.catfactsfeeddemo.view.activities

import android.view.LayoutInflater
import com.jutt.catfactsfeeddemo.core.BaseVVMNavigationActivity
import com.jutt.catfactsfeeddemo.databinding.ActivityHomeBinding
import com.jutt.catfactsfeeddemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

class HomeActivity : BaseVVMNavigationActivity<ActivityHomeBinding,HomeViewModel>() {

    override fun getViewModelClass() = HomeViewModel::class.java

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding
        get() = ActivityHomeBinding::inflate

    override fun onReady() {

    }
}