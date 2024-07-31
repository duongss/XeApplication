package com.dss.xeapplication.base.ads.nativeads

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.dss.xeapplication.App
import com.dss.xeapplication.R
import com.dss.xeapplication.base.ads.Ads
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.wavez.p27_pdf_scanner.data.local.SharedPref

object NativeManager {

    private var hashMapAds = hashMapOf<String, MutableList<NativeAdState>>()
    private var storageAds = StorageAds()

    fun load() {
        storageAds.loadAds()
    }

    fun bindNativeAds(
        fragment: Fragment,
        layoutBound: ViewGroup,
        layoutNative: Int,
    ) {
        fragment.activity?.let {
            bindNativeAds(
                it,
                layoutBound,
                layoutNative,
                fragment.javaClass.simpleName
            )
        } ?: kotlin.run {
            layoutBound.visibility = View.GONE
        }
    }

    fun bindNativeAds(
        activity: FragmentActivity,
        layoutBound: ViewGroup,
        layoutNative: Int,
        keyAd: String = activity.javaClass.simpleName
    ) {
        if (SharedPref.isVip) {
            layoutBound.visibility = View.GONE
            return
        }

        if (activity.isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
            return
        }

        synchronized(layoutBound) {
            val nativeAdState = storageAds.getAds()

            nativeAdState?.let {
                if (hashMapAds.containsKey(keyAd)) {
                    hashMapAds[keyAd]?.add(it)
                } else {
                    hashMapAds[keyAd] = mutableListOf(it)
                }

                val adView = activity.layoutInflater.inflate(layoutNative, null) as NativeAdView
                it.nativeAd?.let { d -> populateNativeAdView(d, adView) }
                layoutBound.removeAllViews()
                layoutBound.addView(adView)
            } ?: kotlin.run {
                layoutBound.visibility = View.GONE
            }
        }
    }

    fun getNativeAds(
        activity: FragmentActivity,
        layoutNative: Int,
        numberAds: Int = 1,
        keyAd: String = activity.javaClass.simpleName
    ): List<NativeAdView>? {

        if (SharedPref.isVip || activity.isDestroyed || activity.isFinishing || activity.isChangingConfigurations) {
            return null
        }

        val listResult = mutableListOf<NativeAdView>()
        repeat(numberAds) {
            storageAds.getAds()?.let {
                if (hashMapAds.containsKey(keyAd)) {
                    hashMapAds[keyAd]?.add(it)
                } else {
                    hashMapAds[keyAd] = mutableListOf(it)
                }

                val adView = activity.layoutInflater.inflate(layoutNative, null) as NativeAdView
                it.nativeAd?.let { d ->
                    listResult.add(populateNativeAdView(d, adView))
                }
            }
        }
        return listResult
    }

    fun clear(nameClass: String) {
        synchronized(nameClass) {
            hashMapAds[nameClass]?.forEach {
                if (it.type != Type.FIXED)
                    it.nativeAd?.destroy()
            }
            hashMapAds[nameClass]?.clear()
        }
    }

    private fun populateNativeAdView(
        nativeAd: NativeAd,
        adView: NativeAdView
    ): NativeAdView {
        // Set the media view.
        adView.mediaView = adView.findViewById(R.id.ad_media)

        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView =
            adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline and media content are guaranteed to be in every UnifiedNativeAd.
        nativeAd.mediaContent?.let {
            adView.mediaView?.setMediaContent(it)
        }

        try {
            if (nativeAd.body == null) {
                adView.bodyView?.visibility = View.INVISIBLE
            } else {
                adView.bodyView?.visibility = View.VISIBLE
                adView.bodyView?.let {
                    (it as TextView).text = nativeAd.body
                }
            }
        } catch (e: Exception) {
            adView.bodyView?.visibility = View.INVISIBLE
        }

        try {
            if (nativeAd.price == null) {
                adView.priceView?.visibility = View.INVISIBLE
            } else {
                adView.priceView?.visibility = View.VISIBLE
                adView.priceView?.let {
                    (it as TextView).text = nativeAd.price
                }
            }
        } catch (e: Exception) {
            adView.priceView?.visibility = View.INVISIBLE
        }

        try {
            if (nativeAd.store == null) {
                adView.storeView?.visibility = View.GONE
            } else {
                adView.storeView?.visibility = View.VISIBLE
                adView.storeView?.let {
                    (it as TextView).text = nativeAd.store
                }
            }
        } catch (e: Exception) {
            adView.storeView?.visibility = View.GONE
        }


        try {
            (adView.headlineView as TextView).text = nativeAd.headline
        } catch (e: Exception) {
            adView.headlineView?.visibility = View.GONE
        }

        try {
            if (nativeAd.advertiser == null) {
                adView.advertiserView?.visibility = View.INVISIBLE
            } else {
                adView.advertiserView?.visibility = View.VISIBLE
                adView.storeView?.let {
                    (it as TextView).text = nativeAd.advertiser
                }
            }
        } catch (e: Exception) {
            adView.advertiserView?.visibility = View.INVISIBLE
        }

        try {
            if (nativeAd.starRating == null) {
                adView.starRatingView?.visibility = View.INVISIBLE
            } else {
                (adView.starRatingView as RatingBar).rating = nativeAd.starRating!!.toFloat()
                adView.starRatingView?.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            adView.starRatingView?.visibility = View.INVISIBLE
        }

        try {
            if (nativeAd.icon == null) {
                adView.iconView?.visibility = View.GONE
            } else {
                adView.iconView?.let {
                    (it as ImageView).setImageDrawable(
                        nativeAd.icon?.drawable
                    )
                    it.visibility = View.VISIBLE
                }
            }
        } catch (e: Exception) {
            adView.iconView?.visibility = View.GONE
        }

        try {
            if (nativeAd.callToAction == null) {
                adView.callToActionView?.visibility = View.INVISIBLE
            } else {
                adView.callToActionView?.let {
                    it.visibility = View.VISIBLE
                    (it as AppCompatTextView).text = nativeAd.callToAction
                }
            }

        } catch (e: Exception) {
            adView.callToActionView?.visibility = View.INVISIBLE
        }

        adView.setNativeAd(nativeAd)
        return adView
    }

}

class StorageAds {
    private val storageStack = ArrayDeque<NativeAdState>()

    private val limitLoad = 3
    private var builder: AdLoader.Builder? = null
    private var data: NativeAdState? = null

    init {
        builder = AdLoader.Builder(App.appContext(), Ads.nativeId)
    }

    fun loadAds() {
        if (limitLoad == storageStack.size || (data?.state == State.LOADING && storageStack.isNotEmpty())) {
            return
        }
        data = NativeAdState()

        if (storageStack.isNotEmpty()) {
            data?.type = Type.DYNAMIC
        }
        builder?.forNativeAd { nativeAd ->
            data!!.nativeAd = nativeAd
            storageStack.add(data!!)
        }
        val adLoader =
            builder?.withAdListener(
                object : AdListener() {
                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        data?.state = State.FAILED
                        loadAds()
                    }

                    override fun onAdLoaded() {
                        super.onAdLoaded()
                        data?.state = State.LOADED
                        loadAds()
                    }
                }
            )?.build()

        adLoader?.loadAd(AdRequest.Builder().build())
    }

    fun getAds(): NativeAdState? {
        val data = when (storageStack.size) {
            0 -> {
                null
            }

            1 -> {
                storageStack.firstOrNull()
            }

            else -> {
                storageStack.removeLast()
            }
        }
        loadAds()

        return data
    }
}

data class NativeAdState(
    var nativeAd: NativeAd? = null,
    var state: State = State.LOADING,
    var type: Type = Type.FIXED
)

enum class State {
    LOADING,
    FAILED,
    LOADED
}

enum class Type {
    FIXED,
    DYNAMIC
}