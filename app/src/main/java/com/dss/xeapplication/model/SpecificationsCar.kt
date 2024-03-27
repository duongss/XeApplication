package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificationsCar(
    var groupTitle: String = "",
    var title: String = "",
    var content : String = "",
    var resultStr : String = "",
    var resultBoolean: Boolean = false,
    var type: Int = TYPE_GROUP_TITLE
) : Parcelable {
    companion object {
        const val TYPE_GROUP_TITLE = 0

        const val TYPE_CONTENT = 1

        const val TYPE_RESULT_BOOLEAN = 2
    }

}