package com.dss.xeapplication.base

import android.os.Bundle

interface BaseInit<T> {
    fun bindingView(): T

    fun initConfig(savedInstanceState: Bundle?)

    fun initObserver()

    fun initListener()

}