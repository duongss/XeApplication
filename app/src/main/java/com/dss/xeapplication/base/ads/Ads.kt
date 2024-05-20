package com.dss.xeapplication.base.ads

import com.dss.xeapplication.BuildConfig


object Ads {
    var isShowFullScreen: Boolean = false
    var fullScreenDismissTime = 0L

    private const val banner_test_id = "ca-app-pub-3940256099942544/6300978111"

    private const val interstitial_test_id = "ca-app-pub-3940256099942544/1033173712"

    private const val interstitial_reward_test_id = "ca-app-pub-3940256099942544/5354046379"

    private const val reward_test_id = "ca-app-pub-3940256099942544/5224354917"

    private const val native_test_id = "ca-app-pub-3940256099942544/2247696110"

    private const val open_test_id = "ca-app-pub-3940256099942544/9257395921"

    val bannerId: String
        get() = if (BuildConfig.DEBUG) banner_test_id else "ca-app-pub-5600863247121843/5131059015"

    val interstitialId: String
        get() = if (BuildConfig.DEBUG) interstitial_test_id else "ca-app-pub-6764133072003430/3693302920"

    val interstitialRewardId: String
        get() = if (BuildConfig.DEBUG) interstitial_reward_test_id else "ca-app-pub-6764133072003430/3054174523"

    val rewardId: String
        get() = if (BuildConfig.DEBUG) reward_test_id else "ca-app-pub-5600863247121843/3912496056"

    val nativeId: String
        get() = if (BuildConfig.DEBUG) native_test_id else "ca-app-pub-5600863247121843/5350550862"

    val openId: String
        get() = if (BuildConfig.DEBUG) open_test_id else "ca-app-pub-5600863247121843/9700859410"

}