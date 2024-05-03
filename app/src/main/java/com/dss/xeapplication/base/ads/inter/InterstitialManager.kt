package com.dss.xeapplication.base.ads.inter

import android.app.Activity
import android.content.Context
import com.dss.xeapplication.base.ads.Ads
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.wavez.p27_pdf_scanner.data.local.SharedPref

interface OnCompletedListener {
    fun onCompleted()

    fun onSuccess() {}
}

object InterstitialManager {
    private var onCompletedListener: OnCompletedListener? = null
    private var mAd: InterstitialAd? = null
    private var adDismissedLastTime = 0L
    private var isLoading: Boolean = false
    private var loadingTimeFailed = 0L
    private var delayLoadingTimeMillis = 0

    private fun canShow(): Boolean {
        if (System.currentTimeMillis() - Ads.fullScreenDismissTime <= SharedPref.minTimeGap) return false
        if (Ads.isShowFullScreen) return false
        if (SharedPref.isVip) return false
        return System.currentTimeMillis() - adDismissedLastTime >= SharedPref.interstitialGap
    }

    fun show(activity: Activity, ls: OnCompletedListener?) {
        onCompletedListener = ls
        val ad = this.mAd
        if (ad == null) {
            load(activity)
            onCompletedListener?.onCompleted()
        } else {
            if (canShow()) {
                mAd = null
                load(activity)
                show(activity, ad)
            } else {
                onCompletedListener?.onCompleted()
            }
        }
    }

    fun forceShow(activity: Activity, ls: OnCompletedListener?) {
        onCompletedListener = ls
        val ad = this.mAd
        if (ad == null) {
            load(activity)
            onCompletedListener?.onCompleted()
        } else {
            show(activity, ad)
        }
    }

    private fun show(activity: Activity, ad: InterstitialAd) {
        this.mAd = null
        Ads.isShowFullScreen = true
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                Ads.isShowFullScreen = false
                onCompletedListener?.onCompleted()
            }
        }
        ad.show(activity)
    }

    fun load(context: Context) {
        isLoading = true
        InterstitialAd.load(
            context, Ads.interstitialId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    onAdLoadFailed()
                }

                override fun onAdLoaded(ad: InterstitialAd) {
                    super.onAdLoaded(ad)
                    mAd = ad
                    delayLoadingTimeMillis = 0
                    isLoading = false
                    loadingTimeFailed = 0
                }
            })
    }

    fun isReady(): Boolean {
        return mAd != null
    }

    private fun onAdDismissed() {
        Ads.isShowFullScreen = false
        Ads.fullScreenDismissTime = System.currentTimeMillis()
        adDismissedLastTime = System.currentTimeMillis()
        onCompletedListener?.onCompleted()
        onCompletedListener?.onSuccess()
        onCompletedListener = null
    }

    private fun onAdLoadFailed() {
        isLoading = false
        delayLoadingTimeMillis = if (delayLoadingTimeMillis == 0) {
            5000
        } else {
            minOf(delayLoadingTimeMillis * 2, 40000)
        }
        loadingTimeFailed = System.currentTimeMillis()
    }
}