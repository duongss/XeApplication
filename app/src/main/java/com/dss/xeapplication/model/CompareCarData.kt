package com.dss.xeapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompareCarData(
    var car1:Car?=null,
    var car2:Car?=null,
    var listCompare: ArrayList<SpecificationsCompareCar> = arrayListOf()
) : Parcelable {

}