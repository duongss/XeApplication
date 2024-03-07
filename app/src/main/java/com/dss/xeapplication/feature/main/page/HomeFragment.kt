package com.dss.xeapplication.feature.main.page

import com.dss.xeapplication.MainActivity
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun bindingView() = FragmentHomeBinding.inflate(layoutInflater)

    companion object {

        private const val PAGE_HOME = 0

        private const val PAGE_TOOL = 1

        fun newInstance() = HomeFragment()

    }


    private val activity by lazy {
        requireActivity() as MainActivity
    }



}