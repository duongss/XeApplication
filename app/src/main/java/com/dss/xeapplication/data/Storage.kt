package com.dss.xeapplication.data

import android.util.Log
import com.dss.xeapplication.App
import com.dss.xeapplication.base.extension.internalFile
import com.dss.xeapplication.model.BrandProvider.ALL
import com.dss.xeapplication.model.BrandProvider.FORD
import com.dss.xeapplication.model.BrandProvider.HONDA
import com.dss.xeapplication.model.BrandProvider.HUYNDAI
import com.dss.xeapplication.model.BrandProvider.KIA
import com.dss.xeapplication.model.BrandProvider.MAZDA
import com.dss.xeapplication.model.BrandProvider.MITSUBISHI
import com.dss.xeapplication.model.BrandProvider.TOYOTA
import com.dss.xeapplication.model.BrandProvider.VINFAST
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

    var markList = arrayListOf<Car>()
    var listCar = arrayListOf<Car>()

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
                    try {
                        ALL.listCar.add(it)

                        when(it.brand.lowercase(Locale.ROOT)){
                            MITSUBISHI.name.name.lowercase(Locale.ROOT) ->{
                                MITSUBISHI.listCar.add(it)
                            }
                            HONDA.name.name.lowercase(Locale.ROOT) ->{
                                HONDA.listCar.add(it)
                            }
                            TOYOTA.name.name.lowercase(Locale.ROOT) ->{
                                TOYOTA.listCar.add(it)
                            }
                            MAZDA.name.name.lowercase(Locale.ROOT) ->{
                                MAZDA.listCar.add(it)
                            }
                            KIA.name.name.lowercase(Locale.ROOT) ->{
                                KIA.listCar.add(it)
                            }
                            VINFAST.name.name.lowercase(Locale.ROOT) ->{
                                VINFAST.listCar.add(it)
                            }
                            HUYNDAI.name.name.lowercase(Locale.ROOT) ->{
                                HUYNDAI.listCar.add(it)
                            }
                            HUYNDAI.name.name.lowercase(Locale.ROOT) ->{
                                HUYNDAI.listCar.add(it)
                            }
                        }
                    }catch (e:Exception){

                    }
                }


                loadMarkFolder()
                withContext(Dispatchers.Main){
                    result(true)
                }
            }

        }.addOnFailureListener {
            result(false)
        }
    }

    private suspend fun loadMarkFolder() = withContext(Dispatchers.IO) {
        val list = ArrayList(App.instance().appDao.getMark())
        markList.clear()

        list.forEach { l ->
            listCar.forEach {
                if (it.id == l.id) {
                    it.isMark = true
                    markList.add(it)
                }
            }
        }
    }
}