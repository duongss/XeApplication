package com.dss.xeapplication.base

data class WrapData<T>(var t: T? = null, val type: AdsType? = null)

data class AdsType(var i: Int = 0)