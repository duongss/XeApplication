package com.dss.xeapplication.data

import android.content.Context
import android.util.Log
import com.dss.xeapplication.App
import com.dss.xeapplication.base.extension.internalFile
import com.dss.xeapplication.model.Car
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object FirebaseStorage {
    const val nameFileJsonCar = "aa.json"

    const val TAG = "FirebaseStorage"

    var listDataCar = arrayListOf<Car>()

    fun initData(result: (Boolean) -> Unit) {
        val context = App.appContext()
        val f = context.internalFile()
        if (f.exists() && f.isDirectory){
            f.deleteRecursively()
        }

        val storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child(nameFileJsonCar)

        val localFile = File.createTempFile("data","json",context.internalFile())

        pathReference.getFile(localFile).addOnSuccessListener {
            val jsonString = localFile.readText()

            val collectionType = object : TypeToken<List<Car>>() {}.type
            val cars: List<Car> = Gson().fromJson(jsonString, collectionType)
            listDataCar = ArrayList(cars)

            result(true)
        }.addOnFailureListener {
            result(false)
        }
    }
}

