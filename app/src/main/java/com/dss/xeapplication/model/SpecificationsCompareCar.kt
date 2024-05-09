package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificationsCompareCar(
    var title: Int?,
    var contentCar1: String? = "",
    var contentIntCar1: Int? = 0,
    var contentBooleanCar1: Boolean = false,

    var contentCar2: String? = "",
    var contentIntCar2: Int? = 0,
    var contentBooleanCar2: Boolean = false,

    var unit: String? = "",
    var type: Int = TYPE_CONTENT
) : Parcelable {
    companion object {
        const val TYPE_CONTENT = 1

        const val TYPE_CONTENT_BOOLEAN = 2

        const val TYPE_CONTENT_INT = 3
    }

}