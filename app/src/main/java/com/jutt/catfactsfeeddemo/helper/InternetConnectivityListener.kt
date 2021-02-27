package com.jutt.catfactsfeeddemo.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.lifecycle.*
import com.jutt.catfactsfeeddemo.utils.NetworkUtils
import javax.inject.Inject

class InternetConnectivityListener @Inject constructor(
    private val applicationContext: Context
) : LifecycleObserver {

    private val _networkStateObserver: MutableLiveData<Boolean> = MutableLiveData()
    val networkStateObserver: LiveData<Boolean> get() = _networkStateObserver

    private val connectivityManager: ConnectivityManager by lazy {
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequest: NetworkRequest by lazy {
        NetworkRequest.Builder().build()
    }

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            updateNetworkState()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            updateNetworkState()
        }

        override fun onUnavailable() {
            super.onUnavailable()
            updateNetworkState()
        }
    }

    private fun updateNetworkState() {
        val isConnected = isConnected(applicationContext)
        _networkStateObserver.postValue(isConnected)
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResumed() {
        updateNetworkState()
        registerCallback()
    }

    @Suppress("unused")
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPaused() {
        unregisterCallback()
    }

    private fun isConnected(context: Context) = NetworkUtils.isNetworkAvailable(context)

    private fun registerCallback() {
        connectivityManager.registerNetworkCallback(networkRequest, callback)
    }

    private fun unregisterCallback() {
        connectivityManager.unregisterNetworkCallback(callback)
    }
}