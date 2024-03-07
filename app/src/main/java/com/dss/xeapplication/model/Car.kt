package com.dss.xeapplication.model

import com.dss.xeapplication.R


data class Car(
    var id : String,
    var name : String, // tên xe
    val brand: String, // hãng
    val model: String, // kiểu
    val year: Int, // năm sản xuất
    val color : Array<Int> = arrayOf(R.color.white),
    var originPrice : Float = 0f,
    var currentPrice : Float = 0f,
    val engineType: Int,   // Loại động cơ
    val transmissionType: Int, // Loại hộp số
    val fuelType: Int, // Loại nhiên liệu
    val numOfDoors: Int,
    val numOfSeats: Int,
    var length : Int = 0,
    var width : Int = 0,
    var height : Int = 0,
    var UndercarriageDistance : Int = 0, //Khoảng cách gầm xe
    var airBag : Int = 0, // số túi khí
    var tirePressureSensor : Boolean = false, // Cảm biến áp suất lốp
    var automaticHeadlights : Boolean = false, // đèn pha tự động
    var BSW : Boolean = false, // Hệ thống cảnh báo điểm mù
    var RCTA : Boolean = false, // Hệ thống cảnh báo phương tiện cắt ngang khi lùi xe
    var LCDN : Boolean = false, // Hệ thống cảnh báo phương tiện phía trước khởi hành
    var ABS : Boolean = false, // Hệ thống chống bó cứng phanh
    var VRS : Boolean = false, // Cảm biến phía sau xe
    var WPCS : Boolean = false, // sạc không dây
    var reverseCamera : Boolean = false, // Camera lùi
    var cruiseControlSystem : Boolean = false, // Hệ thống điều khiển hành trình
    var autoHold : Boolean = false ,// Phanh tay điện tử & Giữ phanh tự động
    var airConditionType : Int, // điều hòa
    var chairMaterial : Int,
    var numberSpeakers : Int = 0
) {
}