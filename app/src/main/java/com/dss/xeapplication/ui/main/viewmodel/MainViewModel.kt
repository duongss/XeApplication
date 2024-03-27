package com.dss.xeapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.ui.main.MainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    var currentPage = MutableLiveData(MainFragment.PAGE_HOME)

    fun setCurrentPage(pageHome: Int) {
        currentPage.value = pageHome
    }
}