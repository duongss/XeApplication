package com.dss.xeapplication.ui.diaglog

import android.os.Bundle
import com.dss.xeapplication.base.BaseBottomSheetDialogFragment
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.databinding.BottomDialogComparePreBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComparePreBottomDialog : BaseBottomSheetDialogFragment<BottomDialogComparePreBinding>() {

    override fun bindingView() = BottomDialogComparePreBinding.inflate(layoutInflater)

    companion object {

        fun newInstance(): ComparePreBottomDialog {
            return ComparePreBottomDialog()
        }

    }


    override fun initConfig(savedInstanceState: Bundle?) {
    }


    override fun initObserver() {

    }

    override fun initListener() {

        binding.rlCar1.onAvoidDoubleClick {

        }

        binding.rlCar2.onAvoidDoubleClick {

        }

        binding.btnNext.onAvoidDoubleClick {
            dismiss()
        }
    }


}