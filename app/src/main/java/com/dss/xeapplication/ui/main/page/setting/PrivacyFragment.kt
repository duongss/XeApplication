package com.dss.xeapplication.ui.main.page.setting

import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentPrivacyBinding

class PrivacyFragment : BaseFragment<FragmentPrivacyBinding>() {
    override fun bindingView() = FragmentPrivacyBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = PrivacyFragment()
    }

    override fun initConfig() {

        binding.TextFeature5.text = buildString {
            append("Privacy Policy:")
            append("\n")
            append("\n")
            append("- The application commits not to collect and use any personal data from the user's device.")
        }

        backListener(binding.ivClose) {
            removeSelf()
        }
    }


}