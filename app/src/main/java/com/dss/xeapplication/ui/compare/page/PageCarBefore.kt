package com.dss.xeapplication.ui.compare.page

import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.databinding.FragmentCompareBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PageCarBefore : BaseFragment<FragmentCompareBinding>() {

    override fun bindingView() = FragmentCompareBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = PageCarBefore()

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