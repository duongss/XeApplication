package com.dss.xeapplication.ui.search

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
import com.dss.xeapplication.ui.main.viewmodel.CarIf
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.checkerframework.checker.units.qual.C
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val carRepository: CarRepository
) : BaseViewModel(savedStateHandle), CarIf {

    val dataCars = MutableLiveData<ArrayList<Car>>()
    var textFilter = ""

    fun filter(text: String = textFilter) {
        val listAll = BrandProvider.ALL.listCar
        val listFilterAll = arrayListOf<Car>()

        listAll.forEach { data ->
            if (data!=null && data.name!=null){
                if (data.name.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                    listFilterAll.add(data)
                }
            }
        }

        listAll.sortByDescending {
            it.name
        }
        textFilter = text
        dataCars.value = listFilterAll
    }

    override fun updateMark(car: Car) {
        viewModelScope.launch(Dispatchers.IO) {
            carRepository.updateMark(car)
        }
    }

}