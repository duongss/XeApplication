package com.dss.xeapplication.base.ads.intereward

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback


open class RewardInter(context: Context, adUnitId: String, var listener: RewardInterListener?) {
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    var isLoading: Boolean = false


    init {
        loadRewardedInterstitialAd(context, adUnitId)
    }

    private fun loadRewardedInterstitialAd(context: Context, adUnitId: String) {
        if (isLoading || rewardedInterstitialAd != null) return
        isLoading = true

        RewardedInterstitialAd.load(context, adUnitId, AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedInterstitialAd) {
                    rewardedInterstitialAd = ad
                    Log.d(TAG, "onAdLoaded")
                    isLoading = false
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedInterstitialAd = null
                    isLoading = false
                    listener?.onLoadFailed()
                    removeListener()
                }
            })
    }

    fun show(activity: Activity) {
        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd!!.show(activity) { rewardItem ->
                rewardedInterstitialAd?.fullScreenContentCallback =
                    object : FullScreenContentCallback() {
                        override fun onAdClicked() {
                            // Called when a click is recorded for an ad.
                            Log.d(TAG, "Ad was clicked.")
                        }

                        override fun onAdDismissedFullScreenContent() {
                            listener?.onLoaded()
                            removeListener()
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            Log.e(TAG, "Ad failed to show fullscreen content.")
                            removeListener()
                        }

                        override fun onAdImpression() {
                            Log.d(TAG, "Ad recorded an impression.")
                        }

                        override fun onAdShowedFullScreenContent() {
                            Log.d(TAG, "Ad showed fullscreen content.")
                        }
                    }
            }
        } else {
            listener?.onLoadFailed()
            removeListener()
        }
    }

    fun removeListener() {
        listener = null
        rewardedInterstitialAd = null
    }

    companion object {
        private const val TAG = "RewardInter"
    }

}

interface RewardInterListener {
    fun onLoaded()
    fun onLoadFailed()
}