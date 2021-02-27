package com.jutt.catfactsfeeddemo.utils

import android.content.Context
import android.net.*

object NetworkUtils {

    private const val NETWORK_TRANSPORT_NONE: String = "none"
    private const val NETWORK_TRANSPORT_UNKNOWN: String = "unknown"
    private const val NETWORK_TRANSPORT_WIFI: String = "wifi"
    private const val NETWORK_TRANSPORT_CELLULAR: String = "mobile_data"

    val NETWORK_CONNECTIVITY_REQUEST: NetworkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            capabilities != null && (
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                            || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
                    )
        } else {
            @Suppress("DEPRECATION")
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            activeNetworkInfo?.isConnectedOrConnecting == true
        }
    }

    fun getConnectedNetworkState(connectivityManager: ConnectivityManager): String =
        onNetworkConnectivityChanged(connectivityManager = connectivityManager)

    fun onNetworkCapabilitiesChanged(capabilities: NetworkCapabilities): String =
        getNetworkTransportString(
            hasWifi = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI),
            hasCellularData = capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR),
            hasOtherNetwork = false
        )

    fun onNetworkConnectivityChanged(connectivityManager: ConnectivityManager): String {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val activeNetwork: Network? = connectivityManager.activeNetwork
            val capabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(activeNetwork)
            return onNetworkCapabilitiesChanged(
                capabilities = capabilities ?: return onNetworkConnectivityChangedLegacy(
                    connectivityManager = connectivityManager
                )
            )
        } else {
            @Suppress("DEPRECATION")
            return onNetworkConnectivityChangedLegacy(connectivityManager = connectivityManager)
        }
    }

    // Only for Android OS < M
    private fun onNetworkConnectivityChangedLegacy(connectivityManager: ConnectivityManager): String =
        @Suppress("DEPRECATION")
        getNetworkTransportString(
            hasWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnectedOrConnecting == true,
            hasCellularData = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnectedOrConnecting == true,
            hasOtherNetwork = (connectivityManager.activeNetworkInfo?.isConnectedOrConnecting == true)
        )

    private fun getNetworkTransportString(
        hasWifi: Boolean,
        hasCellularData: Boolean,
        hasOtherNetwork: Boolean
    ): String = when {
        hasWifi && hasCellularData -> "$NETWORK_TRANSPORT_WIFI|$NETWORK_TRANSPORT_CELLULAR"
        hasCellularData -> NETWORK_TRANSPORT_CELLULAR
        hasWifi -> NETWORK_TRANSPORT_WIFI
        hasOtherNetwork -> NETWORK_TRANSPORT_UNKNOWN
        else -> NETWORK_TRANSPORT_NONE
    }
}
