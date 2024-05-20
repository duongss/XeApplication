package com.dss.xeapplication.model

import com.dss.xeapplication.R


data class SettingP(
    var name: Int,
    var image: Int
)

object SettingPProvider {

    val RATE = SettingP(
        R.string.rate_us,R.drawable.ic_flag
    )

    val FEEDBACK_CAR = SettingP(
        R.string.feedback_car,R.drawable.ic_share_us
    )

    val SHARE_APP = SettingP(
        R.string.share_app,R.drawable.ic_share
    )

    val PRIVACY_POLICY = SettingP(
        R.string.privacy_policy,R.drawable.ic_security
    )

    val EXIT = SettingP(
        R.string.exit,R.drawable.ic_exit
    )

    val listSetting = arrayListOf(RATE, FEEDBACK_CAR, SHARE_APP, PRIVACY_POLICY, EXIT)
}
