package com.dss.xeapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

open class BaseViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val isShowLoading = MutableLiveData<Boolean>()

    fun showLoading() {
        isShowLoading.postValue(true)
    }

    fun dismissLoading() {
        isShowLoading.postValue(false)
    }

}