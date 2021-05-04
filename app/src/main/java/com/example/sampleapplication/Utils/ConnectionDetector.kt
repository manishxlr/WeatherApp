package com.example.sampleapplication.Utils

import android.content.Context
import android.net.ConnectivityManager

class ConnectionDetector {

    private var mConnectionDetector : ConnectionDetector? = null
    private var isConnectedToNetwork = false

    @Synchronized
    fun getConnectionDetector(): ConnectionDetector? {
        if(mConnectionDetector==null){
            mConnectionDetector = ConnectionDetector()
        }
        return mConnectionDetector
    }

    fun isConnectedToInternet(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        isConnectedToNetwork = activeNetwork != null && activeNetwork.isConnected
        return isConnectedToNetwork
    }
}