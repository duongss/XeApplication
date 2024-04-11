package com.dss.xeapplication.ui.detailcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.data.respository.CarRepository
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.SpecificationsCar
import com.dss.xeapplication.ui.main.MainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

interface DetailCarIf {
    fun updateMark(car: Car)
}
@HiltViewModel
class DetailCarViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val carRepository: CarRepository
) : BaseViewModel(savedStateHandle),DetailCarIf {

    var listDetail = MutableLiveData<SpecificationsCar>()
    fun initCar(car: Car) {

    }

    override fun updateMark(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.updateMark(car)
        }
    }

}