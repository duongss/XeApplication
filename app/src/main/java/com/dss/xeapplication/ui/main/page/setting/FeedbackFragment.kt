package com.dss.xeapplication.ui.main.page.setting

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentFeedbackBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : BaseFragment<FragmentFeedbackBinding>() {
    override fun bindingView() = FragmentFeedbackBinding.inflate(layoutInflater)

    companion object {

        const val CONTACT_EMAIL = "duongsscc@gmail.com"
        private const val KEY_SEND_FEEDBACK = "key_send_feed_back"

        fun newInstance() = FeedbackFragment()
    }

    private var sendFeedback = false

    private var feedbackType: String = ""

    override fun initConfig() {
        binding.toolbar.title.text = getString(R.string.str_title_feed_back)
        binding.toolbar.root.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.white))
        try {
            binding.edtFeedback.requestFocus()
            val keyboard = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(binding.edtFeedback, InputMethodManager.SHOW_IMPLICIT)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun initListener() {
        binding.toolbar.btnBack.setOnClickListener {
            removeSelf()
        }

        backListener(binding.toolbar.btnBack) {
            removeSelf()
        }

        binding.btnSendFeedback.onAvoidDoubleClick {
            val feedbackContent = binding.edtFeedback.text
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"))
            val emailList = arrayOf(CONTACT_EMAIL)
            emailIntent.putExtra(Intent.EXTRA_EMAIL, emailList)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback-$feedbackType")
            emailIntent.putExtra(Intent.EXTRA_TEXT, feedbackContent)
            try {
                startActivity(
                    Intent.createChooser(
                        emailIntent, getString(R.string.str_title_feed_back)
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            sendFeedback = true
        }


    }



}