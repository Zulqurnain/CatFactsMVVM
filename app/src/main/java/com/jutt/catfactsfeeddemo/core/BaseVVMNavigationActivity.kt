package com.jutt.catfactsfeeddemo.core

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.KClass

abstract class BaseVVMNavigationActivity<VB : ViewBinding, VM : ViewModel> : AppNavigationActivity() {

    protected val viewModel: VM by lazy { ViewModelProvider(this).get(getViewModelClass()) }
    protected abstract fun getViewModelClass(): Class<VM>
    protected fun getViewModelFactory(): (() -> ViewModelProvider.Factory)? = null

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        onReady()
        onBindLiveData()
    }

    /**
     * Here we may bind our observers to LiveData if some.
     * This method will be executed after parent [onCreate] method
     */
    protected open fun onReady() {
        //Optional
    }
    /**
     * Here we may bind our observers to LiveData if some.
     * This method will be executed after parent [onCreate] method
     */
    protected open fun onBindLiveData() {
        //Optional
    }

    protected fun <T, LD : LiveData<T>> observeNullable(liveData: LD, onChanged: (T?) -> Unit) {
        liveData.observe(this, {
            onChanged(it)
        })
    }

    protected fun <T, LD : LiveData<T>> observe(liveData: LD, onChanged: (T) -> Unit) {
        liveData.observe(this, {
            it?.let(onChanged)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}