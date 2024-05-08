package com.dss.xeapplication.ui.compare

import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentCompareBinding
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

    private val viewModel by activityViewModels<MainViewModel>()

    override fun initConfig() {
        super.initConfig()

    }


    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()


        backListener(binding.ivClose) {
            removeSelf()
        }
    }

}