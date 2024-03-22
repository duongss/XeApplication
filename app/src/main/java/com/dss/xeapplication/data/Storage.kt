package com.dss.xeapplication.data

import com.dss.xeapplication.App
import com.dss.xeapplication.base.extension.internalFile
import com.dss.xeapplication.data.BrandProvider.HONDA
import com.dss.xeapplication.data.BrandProvider.MITSUBISHI
import com.dss.xeapplication.model.Car
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale

object FirebaseStorage {
    const val nameFileJsonCar = "DataCar.json"

    const val TAG = "FirebaseStorage"

    var listCar = arrayListOf<Car>()
    var listBrand = listOf<String>()

    fun initData(result: (Boolean) -> Unit) {

        val context = App.appContext()

        val f = context.internalFile()
        if (f.exists() && f.isDirectory) {
            f.deleteRecursively()
        }

        val storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child(nameFileJsonCar)

        val localFile = File.createTempFile("DataCar", "json", context.internalFile())

        pathReference.getFile(localFile).addOnSuccessListener {
            CoroutineScope(Dispatchers.IO).launch {
                val jsonString = localFile.readText()

                val collectionType = object : TypeToken<List<Car>>() {}.type
                val cars: List<Car> = Gson().fromJson(jsonString, collectionType)
                listCar = ArrayList(cars)

                listCar.forEach {
                    when(it.brand.lowercase(Locale.ROOT)){
                        context.getString(MITSUBISHI.name).lowercase(Locale.ROOT) ->{
                            MITSUBISHI.listCar.add(it)
                        }
                        context.getString(HONDA.name).lowercase(Locale.ROOT) ->{
                            HONDA.listCar.add(it)
                        }
                    }
                }

                listBrand = listCar.map { it.brand }.distinct()

                withContext(Dispatchers.Main){
                    result(true)
                }
            }

        }.addOnFailureListener {
            result(false)
        }
    }
}