package com.dss.xeapplication.model


data class Brand(
    var name: String,
    var image: Int,
    var listCar: ArrayList<Car> = arrayListOf()
) {

}
