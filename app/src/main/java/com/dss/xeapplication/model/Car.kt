package com.dss.xeapplication.model

import android.os.Parcelable
import com.dss.xeapplication.R
import kotlinx.parcelize.Parcelize
import java.util.Locale

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
    var undercarriageDistance  : Int =0, //Khoảng cách gầm xe
    var airBag : Int = 0, // số túi khí
    var tirePressureSensor : Boolean = false, // Cảm biến áp suất lốp
    var headlights : String = "", // đèn pha tự động
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
    // new update
    var numberSpeakers: String = "", // loa
    var entertainmentScreen: String = "", // Màn hình giải trí

    var isMark :Boolean = false,
    var isSelected :Boolean = false
) : Parcelable {
    fun getConvenient(): Int {
        val l = listOf(BSW, RCTA, LCDN, ABS, VRS, WPCS)

        val countTrue = l.count { it }

        return when {
            countTrue >= 5 -> {
                R.string.high
            }

            countTrue >= 3 -> {
                R.string.medium
            }

            else -> {
                R.string.low
            }
        }
    }

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

fun Car.createListSpecifications(): ArrayList<SpecificationsCar> {
    val list = arrayListOf<SpecificationsCar>()

    list.add(SpecificationsCar(R.string.engineType, engineType))
    list.add(SpecificationsCar(R.string.transmissionType, transmissionType))
    list.add(SpecificationsCar(R.string.fuelType, fuelType))
    list.add(SpecificationsCar(R.string.headlights, headlights))
    if (!HT.isNullOrEmpty())
        list.add(SpecificationsCar(R.string.HT, HT))
    list.add(SpecificationsCar(R.string.brake, brake))
    list.add(SpecificationsCar(R.string.airConditionType, airConditionType))
    list.add(SpecificationsCar(R.string.chairMaterial, chairMaterial))
    if (!numberSpeakers.isNullOrEmpty())
        list.add(SpecificationsCar(R.string.speakers_system, numberSpeakers))
    if (!entertainmentScreen.isNullOrEmpty())
        list.add(SpecificationsCar(R.string.entertainmentScreen, entertainmentScreen))

    list.add(
        SpecificationsCar(
            R.string.numOfDoors,
            contentInt = numOfDoors,
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.numOfSeats,
            contentInt = numOfSeats,
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.length,
            contentInt = length,
            unit = "mm",
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.width,
            contentInt = width,
            unit = "mm",
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.height,
            contentInt = height,
            unit = "mm",
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.undercarriageDistance,
            contentInt = undercarriageDistance,
            unit = "mm",
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )
    list.add(
        SpecificationsCar(
            R.string.airBag,
            contentInt = airBag,
            type = SpecificationsCar.TYPE_CONTENT_INT
        )
    )

    list.add(
        SpecificationsCar(
            R.string.BSW,
            contentBoolean = BSW,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )
    list.add(
        SpecificationsCar(
            R.string.RCTA,
            contentBoolean = RCTA,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )
    list.add(
        SpecificationsCar(
            R.string.LCDN,
            contentBoolean = LCDN,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )
    list.add(
        SpecificationsCar(
            R.string.ABS,
            contentBoolean = ABS,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )
    list.add(
        SpecificationsCar(
            R.string.VRS,
            contentBoolean = VRS,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )
    list.add(
        SpecificationsCar(
            R.string.WPCS,
            contentBoolean = WPCS,
            type = SpecificationsCar.TYPE_CONTENT_BOOLEAN
        )
    )

    return list
}

fun Car.nameWithModel(): String {
    return buildString {
        append(name)
        append(" ")
        append(model)
    }
}


fun String.toPrice(): Float {
    var r = 0f
    var r1 = 0f
    val str = this.trim().toLowerCase(Locale.ROOT)

    try {

        if (this.isEmpty()) {
            return 0f
        }

        if (str.contains("tỉ")) {
            val x = split("tỉ")[0]
            r = x.toFloat() * 1000000000
        }

        if (str.contains("triệu")) {
            val x = split("triệu")[0]
            r1 = x.toFloat() * 1000000
        }
    } catch (e: Exception) {

    }

    return r + r1
}