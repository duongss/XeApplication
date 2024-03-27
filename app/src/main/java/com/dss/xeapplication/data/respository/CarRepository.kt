package com.dss.xeapplication.data.respository

import com.dss.xeapplication.data.FirebaseStorage.markList
import com.dss.xeapplication.data.room.AppDao
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Mark

interface CarRepository {
    suspend fun updateMark(car: Car)
}

class CarRepositoryImpl(val dao: AppDao) : CarRepository {
    override suspend fun updateMark(car: Car){
        val isMark = car.isMark
        if (isMark) {
            markList.add(car)
        } else {
            markList.remove(car)
        }
        if (isMark) {
            dao.insertMark(Mark(car.id))
        } else {
            dao.deleteMark(car.id)
        }
    }
}