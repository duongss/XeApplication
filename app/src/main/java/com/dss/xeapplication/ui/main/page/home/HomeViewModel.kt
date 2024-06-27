package com.dss.xeapplication.ui.main.page.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.data.respository.CarRepository
import com.dss.xeapplication.model.Brand
import com.dss.xeapplication.model.BrandProvider
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Sorter
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


interface HomeIf {
    fun updateMark(car: Car)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val carRepository: CarRepository
) : BaseViewModel(savedStateHandle), HomeIf {

    val LIST_ALL = 1
    val LIST_FAV = 2

    val dataCars = MutableLiveData<ArrayList<Car>>()
    var brandSelect = BrandProvider.ALL
    var listMode = LIST_ALL



    init {
        if (BrandProvider.ALL.listCar.isEmpty()) {
            FirebaseStorage.initData {
                initDataCars()
            }
        } else {
            initDataCars()
        }
    }

    fun initDataCars(brand: Brand = brandSelect) = viewModelScope.launch(Dispatchers.IO) {
        if (isShowLoading.value == true) {
            return@launch
        }
        showLoading()
        brandSelect = brand
        val listData = if (listMode == LIST_FAV) ArrayList(brand.listCar.filter { it.isMark })  else ArrayList(brand.listCar)
        val filter = SharedPref.sorterLocal


        if (filter.usingTime) {
            when (filter.timeFilter) {
                Sorter.TimeFilter.NEW_TO_OLDEST -> {
                    listData.sortByDescending { it.year }
                }

                Sorter.TimeFilter.OLDEST_TO_NEW -> {
                    listData.sortBy { it.year }
                }
            }
        } else if (filter.usingPrice) {
            when (filter.priceFilter) {
                Sorter.PriceFilter.CHEAP_TO_EX -> {
                    listData.sortBy { it.currentPrice }
                }

                Sorter.PriceFilter.EX_TO_CHEAP -> {
                    listData.sortByDescending { it.currentPrice }
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

    override fun updateMark(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.updateMark(car)
        }
    }
}