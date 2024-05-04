package com.dss.xeapplication.ui.compare.page

import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ViewPagerAdapter
import com.dss.xeapplication.databinding.FragmentCarResultCompareBinding
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PageResult : BaseFragment<FragmentCarResultCompareBinding>() {

    override fun bindingView() = FragmentCarResultCompareBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = PageResult()

    }

    private lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var arrayTabFragment: ArrayList<BaseFragment<*>>

    private val viewModel by activityViewModels<MainViewModel>()

    override fun initConfig() {
        super.initConfig()


    }


    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()

    }

}