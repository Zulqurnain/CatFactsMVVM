package com.jutt.catfactsfeeddemo.view.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jutt.catfactsfeeddemo.core.BaseVVMFragment
import com.jutt.catfactsfeeddemo.databinding.FragmentHomeBinding
import com.jutt.catfactsfeeddemo.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseVVMFragment<FragmentHomeBinding, HomeViewModel>() {

    override fun getSharedViewModelClass() = HomeViewModel::class.java
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onReady() {

    }

}