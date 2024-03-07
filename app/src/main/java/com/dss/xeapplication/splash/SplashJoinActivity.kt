package com.dss.xeapplication.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.dss.xeapplication.MainActivity
import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.base.ads.GoogleMobileAdsConsentManager
import com.dss.xeapplication.base.network.NetworkHelper
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.databinding.ActivityLaunchBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashJoinActivity : BaseActivity<ActivityLaunchBinding>() {

    override fun bindingView(): ActivityLaunchBinding {
        return ActivityLaunchBinding.inflate(layoutInflater)
    }

    companion object {

        private const val MAX_TIME_DISPLAY: Long = 5000

        private const val MIN_TIME_DISPLAY: Long = 3000
    }

    private var totalProgress = 0f

    private var handler: Handler? = null

    private var loadDataDone = false

    private var isSplashDone = false

    private var isGather: Boolean = false

    @Inject
    lateinit var googleMobileAdsConsentManager: GoogleMobileAdsConsentManager

    private val runnable = object : Runnable {
        override fun run() {
            if (isSplashDone) return
            if (!isGather) return

            if (considerSkipSplash()) {
                nextSplash()
                return
            } else {
                if (totalProgress <= MAX_TIME_DISPLAY) {
                    if (totalProgress >= MIN_TIME_DISPLAY) {
                        nextSplash()
                        return
                    }
                } else {
                    nextSplash()
                }
            }

            totalProgress += 100
            handler?.postDelayed(this, 100)
        }
    }

    private fun considerSkipSplash(): Boolean {
        if (loadDataDone) {
            if (!NetworkHelper.isConnected()) {
                return true
            }
            if (isGather) {
                return true
            }
        } else {

        }
        return false
    }

    override fun onStart() {
        super.onStart()
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(runnable, 0)
    }

    override fun onStop() {
        handler?.removeCallbacksAndMessages(null)
        super.onStop()
    }

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)
        FirebaseStorage.initData {

        }

        googleMobileAdsConsentManager.gatherConsent(this) { consentError ->
            isGather = true
            if (consentError != null) {
                Log.w("consentError", "${consentError.errorCode}: ${consentError.message}")
            }

            if (googleMobileAdsConsentManager.canRequestAds) {

                googleMobileAdsConsentManager.initializeMobileAdsSdk(this)
            } else {
            }
            handler = Handler(Looper.getMainLooper())
            handler?.postDelayed(runnable, 0)
        }

        if (googleMobileAdsConsentManager.canRequestAds) {
            googleMobileAdsConsentManager.initializeMobileAdsSdk(this)
        }
//        Glide.with(this).load("file:///android_asset/launcher_splash.png").into(binding.iconSplash)

    }


    private fun nextSplash() {
        isSplashDone = true
        runOnUiThread {
            startActivity(MainActivity.newIntent(this@SplashJoinActivity))
            finish()
        }
    }


}