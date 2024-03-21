package com.dss.xeapplication.feature.main.page.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.feature.main.MainFragment
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Sorter
import com.google.android.gms.auth.api.signin.internal.Storage
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    val dataCars = MutableLiveData<ArrayList<Car>>()

    init {
        if (FirebaseStorage.listCar.isEmpty()){
            FirebaseStorage.initData {
                 initDataCars()
            }
        }else{
            initDataCars()
        }
    }
    fun initDataCars() = viewModelScope.launch (Dispatchers.IO){
        if (isShowLoading.value == true) {
            return@launch
        }
        showLoading()

        val listData = ArrayList(FirebaseStorage.listCar)
        val filter = SharedPref.sorterLocal

        if (filter.usingTime) {
            when (filter.timeFilter) {
                Sorter.TimeFilter.NEW_TO_OLDEST -> {
                    listData.sortBy { it.year }
                }
                Sorter.TimeFilter.OLDEST_TO_NEW -> {
                    listData.sortByDescending { it.year }
                }
            }
        } else {
            //using Name
            when (filter.nameFilter) {
                Sorter.NameFilter.A_Z -> {
                    listData.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
                }
                Sorter.NameFilter.Z_A -> {
                    listData.sortWith(compareByDescending(String.CASE_INSENSITIVE_ORDER) { it.name })
                }
            }
        }

        dataCars.postValue(listData)
        dismissLoading()
    }
}