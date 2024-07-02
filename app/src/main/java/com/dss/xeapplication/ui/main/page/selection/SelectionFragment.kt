package com.dss.xeapplication.ui.main.page.selection

import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentSelectionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {

    override fun bindingView() = FragmentSelectionBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): SelectionFragment {
            return SelectionFragment()

        }
    }

    override fun initConfig() {
        super.initConfig()
        binding.toolbar.title.text = getString(R.string.data_with_tax)
        binding.toolbar.btnBack.gone()

    }

    override fun initListener() {
        backListener(binding.toolbar.btnBack) {
            removeSelf()
        }

        binding.btnDone.onAvoidDoubleClick {
            removeSelf()
        }
    }
}