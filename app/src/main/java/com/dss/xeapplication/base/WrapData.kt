package com.dss.xeapplication.base

data class WrapData<T>(var t: T? = null, val type: Type = Type.FIRST) {
    enum class Type(i: Int) {
        FIRST(0),
        TWO(1)
    }
}