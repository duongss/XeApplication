package com.dss.xeapplication.ui.main.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseViewModel
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.data.respository.CarRepository
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.CompareCarData
import com.dss.xeapplication.model.SpecificationsCar
import com.dss.xeapplication.model.SpecificationsCompareCar
import com.dss.xeapplication.model.createListSpecifications
import com.dss.xeapplication.model.toPrice
import com.dss.xeapplication.ui.main.MainFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject

interface CarIf {
    fun updateMark(car: Car)
}
@HiltViewModel
class MainViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val carRepository: CarRepository
) : BaseViewModel(savedStateHandle),CarIf {

    companion object{
        const val STATE_CLOSE_PICK_COMPARE = 0
        const val STATE_PICK_COMPARE_CAR_1 = 1
        const val STATE_PICK_COMPARE_CAR_2 = 2
    }

    var stateCompare = MutableLiveData(STATE_CLOSE_PICK_COMPARE)

    var currentPage = MutableLiveData(MainFragment.PAGE_HOME)

    var compareCarData = MutableLiveData(CompareCarData())

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

    fun createListCompare() = viewModelScope.launch(Dispatchers.IO) {
        if (compareCarData.value?.car1 != null && compareCarData.value?.car2 != null) {
            val car1 = compareCarData.value?.car1!!
            val car2 = compareCarData.value?.car2!!

            compareCarData.value?.listCompare = arrayListOf(
                SpecificationsCompareCar(
                    R.string.engineType,
                    contentCar1 = car1.engineType,
                    contentCar2 = car2.engineType
                ),
                SpecificationsCompareCar(
                    R.string.transmissionType,
                    contentCar1 = car1.transmissionType,
                    contentCar2 = car2.transmissionType
                ),
                SpecificationsCompareCar(
                    R.string.fuelType,
                    contentCar1 = car1.fuelType,
                    contentCar2 = car2.fuelType
                ),
                SpecificationsCompareCar(
                    R.string.headlights,
                    contentCar1 = car1.headlights,
                    contentCar2 = car2.headlights
                ),
                SpecificationsCompareCar(R.string.HT, contentCar1 = car1.HT, contentCar2 = car2.HT),
                SpecificationsCompareCar(
                    R.string.brake,
                    contentCar1 = car1.brake,
                    contentCar2 = car2.brake
                ),
                SpecificationsCompareCar(
                    R.string.airConditionType,
                    contentCar1 = car1.airConditionType,
                    contentCar2 = car2.airConditionType
                ),
                SpecificationsCompareCar(
                    R.string.chairMaterial,
                    contentCar1 = car1.chairMaterial,
                    contentCar2 = car2.chairMaterial
                ),

                SpecificationsCompareCar(
                    R.string.numOfDoors,
                    contentIntCar1 = car1.numOfDoors,
                    contentIntCar2 = car2.numOfDoors,
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.numOfSeats,
                    contentIntCar1 = car1.numOfSeats,
                    contentIntCar2 = car2.numOfSeats,
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.length,
                    contentIntCar1 = car1.length,
                    contentIntCar2 = car2.length,
                    unit = "mm",
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.width,
                    contentIntCar1 = car1.width,
                    contentIntCar2 = car2.width,
                    unit = "mm",
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.height,
                    contentIntCar1 = car1.height,
                    contentIntCar2 = car2.height,
                    unit = "mm",
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.undercarriageDistance,
                    contentIntCar1 = car1.undercarriageDistance,
                    contentIntCar2 = car2.undercarriageDistance,
                    unit = "mm",
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),
                SpecificationsCompareCar(
                    R.string.airBag,
                    contentIntCar1 = car1.airBag,
                    contentIntCar2 = car2.airBag,
                    type = SpecificationsCar.TYPE_CONTENT_INT
                ),

                SpecificationsCompareCar(
                    R.string.BSW,
                    contentBooleanCar1 = car1.BSW,
                    contentBooleanCar2 = car2.BSW,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
                SpecificationsCompareCar(
                    R.string.RCTA,
                    contentBooleanCar1 = car1.RCTA,
                    contentBooleanCar2 = car2.RCTA,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
                SpecificationsCompareCar(
                    R.string.LCDN,
                    contentBooleanCar1 = car1.LCDN,
                    contentBooleanCar2 = car2.LCDN,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
                SpecificationsCompareCar(
                    R.string.ABS,
                    contentBooleanCar1 = car1.ABS,
                    contentBooleanCar2 = car2.ABS,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
                SpecificationsCompareCar(
                    R.string.VRS,
                    contentBooleanCar1 = car1.VRS,
                    contentBooleanCar2 = car2.VRS,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
                SpecificationsCompareCar(
                    R.string.WPCS,
                    contentBooleanCar1 = car1.WPCS,
                    contentBooleanCar2 = car2.WPCS,
                    type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
                ),
            )
            compareCarData.postValue(compareCarData.value)
        }
    }

    fun updateStateCompare(state : Int){
        when(state){
            STATE_PICK_COMPARE_CAR_1->{
                updateCar1(null)
            }

            STATE_PICK_COMPARE_CAR_2->{
                updateCar2(null)
            }

            else->{

            }
        }
        stateCompare.value = state
    }

    fun updateCar1(car: Car?){
        compareCarData.value?.car1 = car

        if (compareCarData.value?.car2 == null){
            stateCompare.value = STATE_PICK_COMPARE_CAR_2
        }
    }

    fun updateCar2(car: Car?){
        compareCarData.value?.car2 = car

        if (compareCarData.value?.car1 == null){
            stateCompare.value = STATE_PICK_COMPARE_CAR_1
        }
    }

    val listConvenient = listOf(R.string.low,R.string.medium,R.string.high)
    val listTypeFuel = listOf(R.string.oil_or_gas, R.string.electric)
    val listBottomType = listOf(R.string.bottom_high,R.string.bottom_low)
    val listSeat = FirebaseStorage.listCar.map { it.numOfSeats }.toSet().sortedDescending()
    var listResultSelection = MutableStateFlow(arrayListOf<Car>())

    fun thinkData(
        context: Context,
        checkedChipConvenient: String,
        checkedChipFuel: String,
        checkedChipSeat: Int,
        checkedChipBottom: String,
        price: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val priceE = price.toPrice()

        val listResult = arrayListOf<Car>()
        val lastListResult = arrayListOf<Car>()

        val pairRangePrice = Pair(priceE - 100000000, priceE + 100000000)
        FirebaseStorage.listCar.forEach {
            if (priceE != 0f) {
                if (it.currentPrice.toPrice() < pairRangePrice.first) {
                    return@forEach
                }
                if (it.currentPrice.toPrice() > pairRangePrice.second) {
                    return@forEach
                }
            }
            if (checkedChipFuel.isNotEmpty() && checkedChipFuel.split(" ").none { c ->
                    c.lowercase(Locale.getDefault())
                        .contains(it.fuelType.lowercase(Locale.getDefault()))
                }) {
                return@forEach
            }

            if (checkedChipBottom.isNotEmpty() && checkedChipBottom == context.getString(R.string.bottom_high) && it.undercarriageDistance < 180) {
                return@forEach
            }

            if (checkedChipConvenient.isNotEmpty() && checkedChipConvenient != context.getString(it.getConvenient())) {
                return@forEach
            }

            listResult.add(it)
        }

        if (listResult.isNotEmpty()) {
            listResult.forEach {
                if (checkedChipSeat != it.numOfSeats && checkedChipSeat > 0) {
                    return@forEach
                }
                lastListResult.add(it)
            }
        }

        if (lastListResult.isEmpty()) {
            lastListResult.addAll(listResult)
        }

        listResultSelection.value = lastListResult
    }
}