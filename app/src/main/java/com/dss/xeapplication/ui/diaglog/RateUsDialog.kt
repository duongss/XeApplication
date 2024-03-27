package com.dss.xeapplication.ui.diaglog

import android.content.Context
import android.text.TextUtils
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseDialogFragment
import com.dss.xeapplication.base.extension.linkAppStore
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.databinding.DialogRateUsBinding
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateUsDialog : BaseDialogFragment<DialogRateUsBinding>() {

    override fun bindingView() = DialogRateUsBinding.inflate(layoutInflater)

    companion object {

        fun newInstance(): RateUsDialog {
            return RateUsDialog()
        }
    }

    private var isRateLater = true

    private var rate = 5f

    private var listener: OnRateListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnRateListener) {
            listener = context
        } else {
            try {
                val parFragment = requireParentFragment()
                if (parFragment is OnRateListener) {
                    listener = parFragment
                }
            } catch (e: Exception) {
            }
        }
    }

    override fun initConfig() {
        binding.btnLater.ellipsize = TextUtils.TruncateAt.MARQUEE
        binding.btnRate.ellipsize = TextUtils.TruncateAt.MARQUEE

        binding.btnLater.isSelected = true
        binding.btnRate.isSelected = true

    }

    override fun initListener() {

        binding.ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            rate = rating
            when (rate) {
                1f -> {
                    binding.imgStateRate.setImageResource(R.drawable.ic_rate_1)
                }
                2f -> {
                    binding.imgStateRate.setImageResource(R.drawable.ic_rate_2)
                }
                3f -> {
                    binding.imgStateRate.setImageResource(R.drawable.ic_rate_3)
                }
                4f -> {
                    binding.imgStateRate.setImageResource(R.drawable.ic_rate_4)
                }
                5f -> {
                    binding.imgStateRate.setImageResource(R.drawable.ic_rate_5)
                }
            }
        }

        binding.btnRate.onAvoidDoubleClick {
            if (rate < 4f) {
                listener?.onFeedBack()
            } else {
                requireContext().linkAppStore()
            }
            SharedPref.isRate = true
            dismiss()
        }

        binding.btnLater.onAvoidDoubleClick {
            isRateLater = true
            dismiss()
        }
    }

    interface OnRateListener {
        fun onFeedBack()

    }


}