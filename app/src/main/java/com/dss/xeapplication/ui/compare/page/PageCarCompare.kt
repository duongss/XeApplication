package com.dss.xeapplication.ui.compare.page

import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.databinding.FragmentCarCompareBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PageCarCompare : BaseFragment<FragmentCarCompareBinding>() {

    override fun bindingView() = FragmentCarCompareBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = PageCarCompare()

    }


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