package com.dss.xeapplication.ui.compare

import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentCompareBinding
import com.dss.xeapplication.ui.compare.page.PageCarCompare
import com.dss.xeapplication.ui.compare.page.PageResult
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompareFragment : BaseFragment<FragmentCompareBinding>() {

    override fun bindingView() = FragmentCompareBinding.inflate(layoutInflater)

    companion object {
        const val PAGE_BEFORE = 0
        const val PAGE_RESULT = 1
        const val PAGE_AFTER = 2

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
            PageCarCompare.newInstance(), PageResult.newInstance(), PageCarCompare.newInstance()
        )
        viewPagerAdapter = ViewPagerAdapter(
            childFragmentManager, lifecycle, arrayTabFragment
        )
        binding.vpData.offscreenPageLimit = arrayTabFragment.size
        binding.vpData.adapter = viewPagerAdapter
        binding.vpData.currentItem = PAGE_RESULT
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