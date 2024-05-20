package com.dss.xeapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class Notification(
    val content: String? = null,
    var isShow:Boolean = true
)