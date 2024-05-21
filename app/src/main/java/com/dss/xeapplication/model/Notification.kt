package com.dss.xeapplication.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Notification(
    val content: String? = null,
    var isShow: Boolean? = null
) {

}