package com.dss.xeapplication.ui.compare

import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentCompareBinding
import com.dss.xeapplication.ui.main.page.home.HomeFragment
import com.dss.xeapplication.ui.main.page.setting.SettingFragment
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompareFragment : BaseFragment<FragmentCompareBinding>() {

    override fun bindingView() = FragmentCompareBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = CompareFragment()

    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var arrayTabFragment: ArrayList<BaseFragment<*>>

    private val viewModel by activityViewModels<MainViewModel>()

    override fun initConfig() {
        super.initConfig()


        initPage()
    }

    private fun initPage() {
        arrayTabFragment = arrayListOf(
            HomeFragment.newInstance(), SettingFragment.newInstance()
        )
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager, lifecycle, arrayTabFragment
        )
        binding.vpData.isUserInputEnabled = false
        binding.vpData.offscreenPageLimit = arrayTabFragment.size
        binding.vpData.adapter = viewPagerAdapter
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()

        binding.btnBack.setOnClickListener {

        }

        binding.btnForward.setOnClickListener {

        }

        backListener(binding.btnClose) {
            removeSelf()
        }
    }

}