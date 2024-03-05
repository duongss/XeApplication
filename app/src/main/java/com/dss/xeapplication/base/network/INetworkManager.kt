package com.dss.xeapplication.base.network

interface INetworkManager {
    fun onNetworkChanged(isConnected: Boolean)
}