package com.dss.xeapplication.data

import android.util.Log
import com.dss.xeapplication.App
import com.dss.xeapplication.base.ads.Ads
import com.dss.xeapplication.base.extension.internalFile
import com.dss.xeapplication.base.network.NetworkHelper
import com.dss.xeapplication.model.BrandProvider.ALL
import com.dss.xeapplication.model.BrandProvider.AUDI
import com.dss.xeapplication.model.BrandProvider.BMW
import com.dss.xeapplication.model.BrandProvider.FORD
import com.dss.xeapplication.model.BrandProvider.HONDA
import com.dss.xeapplication.model.BrandProvider.HUYNDAI
import com.dss.xeapplication.model.BrandProvider.KIA
import com.dss.xeapplication.model.BrandProvider.MAZDA
import com.dss.xeapplication.model.BrandProvider.MITSUBISHI
import com.dss.xeapplication.model.BrandProvider.TOYOTA
import com.dss.xeapplication.model.BrandProvider.VINFAST
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Notification
import com.google.firebase.Firebase
import com.google.firebase.database.database
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
    var listNotification = arrayListOf<Notification>()

    fun initData(result: (Boolean) -> Unit) {

        val context = App.appContext()

        val storage = Firebase.storage
        val storageRef = storage.reference
        val pathReference = storageRef.child(nameFileJsonCar)
        val database = Firebase.database.reference

        val fileData = File(context.internalFile(), nameFileJsonCar)
        if (!NetworkHelper.isConnected() && fileData.exists()) {
            handleFile(fileData, result)
        } else {
            database.get().addOnSuccessListener {
                it.children.forEach {
                    try {
                        when(it.key){
                            "Notification"->{
                                it.children.forEach {
                                    val notification = it.getValue(Notification::class.java)
                                    notification?.let { s -> listNotification.add(s) }
                                }
                            }
                            "isNotShowAds" ->{
                                Ads.isShowFullScreen = it.value as Boolean
                            }
                            else ->{

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }.addOnFailureListener {
                Log.e(TAG, "Error getting data", it)
            }

            pathReference.getFile(fileData).addOnSuccessListener {
                handleFile(fileData, result)
            }.addOnFailureListener {
                handleFile(fileData, result)
            }
        }
    }

    private fun handleFile(localFile: File, result: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var isDone = true

            try {
                val jsonString = localFile.readText()
                val collectionType = object : TypeToken<List<Car>>() {}.type
                val cars: List<Car> = Gson().fromJson(jsonString, collectionType)
                listCar = ArrayList(cars)

                listCar.forEach {
                    ALL.listCar.add(it)

                    when (it.brand.lowercase(Locale.ROOT)) {
                        MITSUBISHI.name.name.lowercase(Locale.ROOT) -> {
                            MITSUBISHI.listCar.add(it)
                        }

                        HONDA.name.name.lowercase(Locale.ROOT) -> {
                            HONDA.listCar.add(it)
                        }

                        TOYOTA.name.name.lowercase(Locale.ROOT) -> {
                            TOYOTA.listCar.add(it)
                        }

                        MAZDA.name.name.lowercase(Locale.ROOT) -> {
                            MAZDA.listCar.add(it)
                        }

                        KIA.name.name.lowercase(Locale.ROOT) -> {
                            KIA.listCar.add(it)
                        }

                        VINFAST.name.name.lowercase(Locale.ROOT) -> {
                            VINFAST.listCar.add(it)
                        }

                        FORD.name.name.lowercase(Locale.ROOT) -> {
                            FORD.listCar.add(it)
                        }

                        HUYNDAI.name.name.lowercase(Locale.ROOT) -> {
                            HUYNDAI.listCar.add(it)
                        }

                        BMW.name.name.lowercase(Locale.ROOT) -> {
                            BMW.listCar.add(it)
                        }

                        AUDI.name.name.lowercase(Locale.ROOT) -> {
                            AUDI.listCar.add(it)
                        }
                    }
                }
                Log.d(TAG, "handleFile: ${listCar.size}")
                loadMarkFolder()

            } catch (e: Exception) {
                isDone = false
            }

            withContext(Dispatchers.Main) {
                result(isDone)
            }
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