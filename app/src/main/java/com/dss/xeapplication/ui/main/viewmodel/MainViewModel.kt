package com.dss.xeapplication.ui.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.data.respository.CarRepository
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.SpecificationsCar
import com.dss.xeapplication.model.createListSpecifications
import com.dss.xeapplication.ui.main.MainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CarIf {
    fun updateMark(car: Car)
}
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val carRepository: CarRepository
) : BaseViewModel(savedStateHandle),CarIf {

    var currentPage = MutableLiveData(MainFragment.PAGE_HOME)

    fun setCurrentPage(pageHome: Int) {
        currentPage.value = pageHome
    }

    var isHiddenBottom = MutableLiveData<Boolean>()

    fun bottomVisible() {
        isHiddenBottom.value = false
    }

    fun bottomHidden() {
        isHiddenBottom.value = true
    }

    override fun updateMark(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.updateMark(car)
        }
    }

    fun createListDetail(carBundle:Car,onResult: (ArrayList<SpecificationsCar>) -> Unit) = viewModelScope.launch(Dispatchers.IO) {
        val data = carBundle.createListSpecifications()
        withContext(Dispatchers.Main){
            onResult(data)
        }
    }
}