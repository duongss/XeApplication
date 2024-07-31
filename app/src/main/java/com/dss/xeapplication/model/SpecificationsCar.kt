package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificationsCar(
    var title: Int?,
    var content : String? = "",
    var contentInt : Int? = 0,
    var unit : String? = "",
    var contentBoolean: Boolean = false,
    var type: Int = TYPE_CONTENT
) : Parcelable {
    companion object {
        const val TYPE_CONTENT = 1

        const val TYPE_CONTENT_BOOLEAN = 2

        const val TYPE_CONTENT_INT = 3

        const val TYPE_ADS = 4


    }

}