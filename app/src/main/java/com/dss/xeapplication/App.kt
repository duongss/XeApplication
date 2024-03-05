package com.dss.xeapplication

import android.app.AlarmManager
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.dss.xeapplication.base.ads.GoogleMobileAdsConsentManager
import com.dss.xeapplication.base.network.INetworkManager
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import com.dss.xeapplication.base.local.bus.Event
import dagger.hilt.android.HiltAndroidApp
import org.greenrobot.eventbus.EventBus
import java.util.Locale
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), INetworkManager {

    var isShowOpenAds = true

    var TfLiteEnable = false

    companion object {

        private lateinit var app: App

        fun instance(): App {
            return app
        }

        fun appContext(): Context = instance().applicationContext
    }

    lateinit var locale: Locale

    @Inject
    lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    fun acceptAds() = googleMobileAdsConsentManager.canRequestAds

    override fun onCreate() {
        super.onCreate()
        app = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


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