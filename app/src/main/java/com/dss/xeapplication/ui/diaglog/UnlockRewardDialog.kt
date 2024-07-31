package com.dss.xeapplication.ui.diaglog

import android.content.Context
import android.content.DialogInterface
import android.os.CountDownTimer
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseDialogFragment
import com.dss.xeapplication.base.ads.Ads
import com.dss.xeapplication.base.ads.intereward.RewardInter
import com.dss.xeapplication.base.ads.intereward.RewardInterListener
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.toastMsg
import com.dss.xeapplication.base.network.NetworkHelper
import com.dss.xeapplication.databinding.DialogUnlockRewardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnlockRewardDialog : BaseDialogFragment<DialogUnlockRewardBinding>() {

    override fun bindingView() = DialogUnlockRewardBinding.inflate(layoutInflater)

    companion object {

        private const val ARG_EFFECT = "effect"
        private const val ARG_BG_COLOR = "arg_bg_color"

        fun newInstance(
            feature: String = "",
            isDark: Boolean = false
        ): UnlockRewardDialog {
            return UnlockRewardDialog().apply {
                arguments = bundleOf(ARG_EFFECT to feature, ARG_BG_COLOR to isDark)
            }
        }
    }

    private var isRequestWatchAd = false
    private var timeDown = true

    var unlockForFreeListener: UnlockForFreeListener? = null

    private var countdownTimer: CountDownTimer? = null

    private lateinit var rewardInter: RewardInter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val fragment = requireParentFragment()
            if (fragment is UnlockForFreeListener) {
                unlockForFreeListener = fragment
            }
        } catch (e: Exception) {
            val activity = requireActivity()
            if (activity is UnlockForFreeListener) {
                unlockForFreeListener = activity
            }
        }
    }

    override fun initConfig() {
        super.initConfig()

        rewardInter =
            RewardInter(requireContext(), Ads.interstitialRewardId, object : RewardInterListener {
                override fun onLoaded() {
                    unlockForFreeListener?.onUnlockedFromUser()
                    dismiss()
                }

                override fun onLoadFailed() {
                    lifecycleScope.launchWhenResumed {
                        unlockForFreeListener?.onUnlockedFromUser()
                        dismiss()
                    }
                }
            })

        if (NetworkHelper.isConnected()) {
            initReward()
        } else {
            binding.btnWatchAd.gone()
        }
    }

    private fun initReward() {

        countdownTimer = object : CountDownTimer(8000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (!isRequestWatchAd) {
                    val secondsRemaining = millisUntilFinished / 1000
                    binding.tvCountDown.text =
                        getString(R.string.ad_is_start_in, (secondsRemaining).toString())
                } else {
                    countdownTimer?.cancel()
                }

            }

            override fun onFinish() {
                if (!isRequestWatchAd && timeDown) {
                    showReward()
                }
            }
        }.start()
    }

    override fun initListener() {
        super.initListener()
        binding.btnBecomeVip.setOnClickListener {
//            isRequestWatchAd = true
//            countdownTimer?.cancel()
//            dismiss()
//            startActivity(VipActivity.newIntent(requireContext()))
            toastMsg(R.string.comming_soon)
        }

        binding.btnWatchAd.setOnClickListener {
            if (rewardInter.isLoading) {
                toastMsg(R.string.wait)

                return@setOnClickListener
            }
            binding.btnWatchAd.gone()
            showReward()
        }

        binding.btnClose.setOnClickListener { dismiss() }
    }

    private fun showReward() {
        isRequestWatchAd = true

        rewardInter.show(requireActivity())
    }


    override fun onDismiss(dialog: DialogInterface) {
        timeDown = false
        isRequestWatchAd = false
        countdownTimer?.cancel()
        super.onDismiss(dialog)
    }

    interface UnlockForFreeListener {
        fun onUnlockedFromUser()
    }

}