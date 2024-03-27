package com.dss.xeapplication.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "mark")
open class Mark(
    @PrimaryKey var id: Int,
    var date: Long = System.currentTimeMillis()
) : Parcelable