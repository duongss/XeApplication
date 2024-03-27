package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    var id : Int = 0,// buộc điền
    var name : String, // tên xe
    val brand: String, // hãng
    val model: String, // kiểu
    val year: String, // năm sản xuất
    val vehicleType: Int = 0,
    var originPrice : String= "",
    var currentPrice : String= "",
    val engineType: String = "",   // Loại động cơ
    val transmissionType: String= "", // Loại hộp số
    val fuelType: String= "", // Loại nhiên liệu
    val numOfDoors: Int,
    val numOfSeats: Int,
    var length : Int = 0, // chiều dài
    var width : Int = 0, // chiều rộng
    var height : Int = 0, // chiều cao
    var UndercarriageDistance : String = "", //Khoảng cách gầm xe
    var airBag : Int = 0, // số túi khí
    var tirePressureSensor : Boolean = false, // Cảm biến áp suất lốp
    var automaticHeadlights  : String = "", // đèn pha tự động
    var BSW : Boolean = false, // Hệ thống cảnh báo điểm mù
    var RCTA : Boolean = false, // Hệ thống cảnh báo phương tiện cắt ngang khi lùi xe
    var LCDN : Boolean = false, // Hệ thống cảnh báo phương tiện phía trước khởi hành
    var ABS : Boolean = false, // Hệ thống chống bó cứng phanh
    var VRS : Boolean = false, // Cảm biến phía sau xe
    var WPCS : Boolean = false, // sạc không dây,
    var HT: String= "", // Hệ thống riêng của xe
    var reverseCamera : Boolean = false, // Camera lùi
    var cruiseControlSystem : Boolean = false, // Hệ thống điều khiển hành trình
    var brake : String = "",// Phanh xe
    var airConditionType : String= "", // điều hòa
    var chairMaterial : String= "",
    var imageCar :String =  "",

    var isMark :Boolean = false,
    var isSelected :Boolean = false
) : Parcelable {

}

enum class VehicleType(val value: Int) {
    Unknown(0),
    Hatchback(1),
    Sedan(2),
    SUV(3),
    MPV (4),
    Coupe  (5),
    Convertible   (6),
    Pickup    (7),
    Limousine     (8);

    companion object {
        fun getType(value: Int): String? {
            return values().find { it.value == value }?.name
        }
    }
}

fun Car.getStrVehicleType(): String? {
    return VehicleType.getType(vehicleType)
}