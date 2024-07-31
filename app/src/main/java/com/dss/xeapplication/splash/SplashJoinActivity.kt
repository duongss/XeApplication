package com.dss.xeapplication.splash

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dss.xeapplication.MainActivity
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.extension.toastMsg
import com.dss.xeapplication.base.network.NetworkHelper
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.databinding.ActivityLaunchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashJoinActivity : BaseActivity<ActivityLaunchBinding>() {

    override fun bindingView(): ActivityLaunchBinding {
        return ActivityLaunchBinding.inflate(layoutInflater)
    }

    companion object {

        private const val MAX_TIME_DISPLAY: Long = 2500

        private const val MIN_TIME_DISPLAY: Long = 2000
    }

    private var totalProgress = 0f

    private var handler: Handler? = null

    private var loadDataDone = false

    private var isSplashDone = false

    private val runnable = object : Runnable {
        override fun run() {
            if (isSplashDone) return

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
        if (NetworkHelper.isConnected()){
            FirebaseStorage.initData {

            }
        }else{
            toastMsg(R.string.no_internet)
        }
        val colorAnim: ObjectAnimator = ObjectAnimator.ofInt(
            binding.tvContent, "textColor",
            Color.WHITE, Color.BLACK
        )
        colorAnim.setDuration(1500)
        colorAnim.setEvaluator(ArgbEvaluator())
        colorAnim.start()

        CoroutineScope(Dispatchers.IO).launch {
            NativeManager.load()
        }
    }


    private fun nextSplash() {
        isSplashDone = true
        runOnUiThread {
            startActivity(MainActivity.newIntent(this@SplashJoinActivity))
            finish()
        }
    }


}