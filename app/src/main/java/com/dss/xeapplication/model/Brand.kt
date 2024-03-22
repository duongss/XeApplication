package com.dss.xeapplication.model


data class Brand(
    var name: Int,
    var image: String,
    var listCar: ArrayList<Car> = arrayListOf()
) {

}