package com.dss.xeapplication.ui.main.page.setting

import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.shareApp
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.databinding.FragmentSettingBinding
import com.dss.xeapplication.model.SettingPProvider
import com.dss.xeapplication.ui.adapter.AdapterSetting
import com.dss.xeapplication.ui.diaglog.RateUsDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    override fun bindingView() = FragmentSettingBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = SettingFragment()
    }

    private lateinit var adapter: AdapterSetting

    override fun initConfig() {
        super.initConfig()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = AdapterSetting()
        adapter.set(SettingPProvider.listSetting)
        binding.rcvData.adapter = adapter

        adapter.onItemSelectListener = { s, itemBrandBinding, i ->
            when (s) {
                SettingPProvider.RATE -> {
                    showChildDialog(RateUsDialog.newInstance())
                }

                SettingPProvider.EXIT -> {
                    requireActivity().finish()
                }

                SettingPProvider.PRIVACY_POLICY -> {
                    addFragment(PrivacyFragment.newInstance())
                }

                SettingPProvider.SHARE_APP -> {
                    requireContext().shareApp()
                }

                SettingPProvider.FEEDBACK_CAR -> {
                    showChildDialog(FeedbackFragment.newInstance())
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()
    }

}