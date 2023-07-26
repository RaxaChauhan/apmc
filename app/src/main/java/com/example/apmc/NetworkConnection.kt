@file:Suppress("DEPRECATION")

package com.example.apmc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

@Suppress("DEPRECATION")
class NetworkConnection(private val context: Context):LiveData<Boolean>() {

    private val connectivityManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback : ConnectivityManager.NetworkCallback

    public override fun onActive() {
        super.onActive()
        updateNetworkConnection()

        when{
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(connectionCallback())
            }
            else->{
                context.registerReceiver(networkReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public override fun onInactive() {
        super.onInactive()
        //connectivityManager.unregisterNetworkCallback(connectionCallback())
    }

    private fun connectionCallback():ConnectivityManager.NetworkCallback{

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }
            }
        }
        return  networkConnectionCallback
    }

    private fun updateNetworkConnection(){
        val networkConnection : NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(networkConnection?.isConnected == true)
    }

    private val networkReceiver = object : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            updateNetworkConnection()
        }

    }
}