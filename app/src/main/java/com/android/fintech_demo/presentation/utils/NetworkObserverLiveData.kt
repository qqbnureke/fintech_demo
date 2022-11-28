package com.android.fintech_demo.presentation.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class NetworkObserverLiveData(context: Context) : LiveData<Boolean>() {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun onActive() {
        super.onActive()
        connectivityManager.registerDefaultNetworkCallback(getNetworkCallback())

        val network = connectivityManager.activeNetwork
        postValue(network != null)
    }


    override fun onInactive() {
        super.onInactive()
        try {
            connectivityManager.unregisterNetworkCallback(getNetworkCallback())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getNetworkCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            CoroutineScope(Dispatchers.IO).launch {
                val hasCapability = checkNetworkCapability(network)
                postValue(hasCapability)
            }
        }

        override fun onCapabilitiesChanged(
            network: Network, networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            postValue(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED))
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            postValue(false)
        }
    }

    private fun checkNetworkCapability(network: Network): Boolean {
        return connectivityManager.getNetworkCapabilities(network)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true
    }
}