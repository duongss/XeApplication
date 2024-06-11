package com.dss.xeapplication.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Notification(
    val content: String? = null,
    var show: Int? = null
) {
    companion object{
        const val SHOW = 1
        const val HIDDEN = 0
    }

}