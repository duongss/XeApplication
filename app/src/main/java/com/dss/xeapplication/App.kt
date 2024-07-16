package com.dss.xeapplication

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.dss.xeapplication.base.local.bus.Event
import com.dss.xeapplication.base.network.INetworkManager
import com.dss.xeapplication.data.room.AppDao
import com.google.firebase.FirebaseApp
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), INetworkManager {

    companion object {

        private lateinit var app: App

        fun instance(): App {
            return app
        }

        fun appContext(): Context = instance().applicationContext
    }

    @Inject
    lateinit var appDao: AppDao

    lateinit var locale: Locale

    override fun onCreate() {
        super.onCreate()
        app = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FirebaseApp.initializeApp(this)


        locale = Locale(SharedPref.getLanguage().toString())
    }




    private fun considerNotify() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                // Temporarily: when an Android 12 device is not granted alarm permission, the notification will not be apply on that device
                return
            }
        }

    }

    override fun onNetworkChanged(isConnected: Boolean) {
        EventBus.getDefault().post(Event.NetworkChangedEvent(isConnected))
    }

}