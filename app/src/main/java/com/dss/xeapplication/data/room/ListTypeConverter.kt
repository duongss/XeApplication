package com.wavez.p27_pdf_scanner.data.room

import android.graphics.PointF
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class ListTypeConverter {
    @TypeConverter
    fun fromPointFList(pointFList: List<PointF>): String {
        return Gson().toJson(pointFList)
    }

    @TypeConverter
    fun toPointFList(json: String): List<PointF> {
        val type = object : TypeToken<List<PointF>>() {}.type
        return Gson().fromJson(json, type)
    }
}