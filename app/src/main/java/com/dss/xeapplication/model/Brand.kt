package com.dss.xeapplication.model


data class Brand(
    var name: Int,
    var image: Int,
    var listCar: ArrayList<Car> = arrayListOf()
) {

}