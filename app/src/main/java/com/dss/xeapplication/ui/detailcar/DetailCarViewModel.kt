package com.dss.xeapplication.ui.detailcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.SpecificationsCar
import com.dss.xeapplication.ui.main.MainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel(savedStateHandle) {

    var listDetail = MutableLiveData<SpecificationsCar>()
    fun initCar(car: Car) {

    }

}