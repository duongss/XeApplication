package com.dss.xeapplication.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dss.xeapplication.BuildConfig
import com.dss.xeapplication.model.Mark
import com.wavez.p27_pdf_scanner.data.room.ListTypeConverter


@Database(
    entities = [Mark::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ListTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): AppDao

    companion object {
        const val DATABASE_NAME = BuildConfig.APPLICATION_ID + "dss.db"
    }

}
