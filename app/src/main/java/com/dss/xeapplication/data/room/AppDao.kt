package com.dss.xeapplication.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dss.xeapplication.model.Mark

@Dao
interface AppDao {

    // query of Recent
//    @Query("SELECT * FROM recent WHERE date >= :startDate ORDER BY date DESC")
//    fun getRecentWithin7Days(startDate: Long): List<Recent>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertRecent(recent: Recent): Long
//
//    @Query("SELECT EXISTS(SELECT 1 FROM recent WHERE path = :path LIMIT 1)") // check path exist
//    fun isRecentPathExists(path: String): Boolean
//
//    @Query("UPDATE recent SET date = :newDate WHERE path = :path")
//    fun updateRecentDateByPath(path: String, newDate: Long)

    // query of Mark
    @Query("SELECT * FROM mark")
    fun getMark(): List<Mark>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMark(mark: Mark): Long

    @Query("DELETE FROM mark WHERE id = :id")
    fun deleteMark(id: Int)



}