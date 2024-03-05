package com.dss.xeapplication.base.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.dss.xeapplication.App

object NetworkHelper {

    var iNetworkManager: INetworkManager? = null

    fun isConnected(): Boolean {
        val cm =
            App.appContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        iNetworkManager?.onNetworkChanged(activeNetwork?.isConnectedOrConnecting == true)
        return activeNetwork?.isConnectedOrConnecting == true
    }

}