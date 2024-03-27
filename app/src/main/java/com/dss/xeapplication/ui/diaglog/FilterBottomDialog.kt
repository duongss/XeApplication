package com.dss.xeapplication.ui.diaglog

import android.content.Context
import android.os.Bundle
import com.dss.xeapplication.base.BaseBottomSheetDialogFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.BottomDialogFilterBinding
import com.dss.xeapplication.model.Sorter
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomDialog : BaseBottomSheetDialogFragment<BottomDialogFilterBinding>() {

    override fun bindingView() = BottomDialogFilterBinding.inflate(layoutInflater)

    companion object {

        const val BUNDLE_DATA = "bundle_data"

        fun newInstance(): FilterBottomDialog {
            return FilterBottomDialog()
        }

    }

    private var listener: FilterListener? = null

    private lateinit var sorter: Sorter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as FilterListener
        } catch (ex: Exception) {
            try {
                listener = requireParentFragment() as FilterListener
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }


    override fun initConfig(savedInstanceState: Bundle?) {
        sorter = SharedPref.sorterLocal
        sync()
    }

    private fun sync() {
        binding.lnTime.gone()
        binding.lnPrice.gone()
        binding.ivCheckTime.isActivated = false
        binding.ivNameCheck.isActivated = false
        binding.ivNewToOldCheck.isActivated = false
        binding.ivOldtoNewCheck.isActivated = false
        binding.ivAzCheck.isActivated = false
        binding.ivZaCheck.isActivated = false

        if (sorter.usingTime) {
            binding.lnTime.visible()
            binding.ivCheckTime.isActivated = true
        }

        if (sorter.usingPrice) {
            binding.lnPrice.visible()
            binding.ivNameCheck.isActivated = true
        }

        when (sorter.timeFilter) {
            Sorter.TimeFilter.NEW_TO_OLDEST -> {
                binding.ivNewToOldCheck.isActivated = true
            }
            else -> {
                binding.ivOldtoNewCheck.isActivated = true
            }
        }
        when (sorter.priceFilter) {
            Sorter.PriceFilter.CHEAP_TO_EX -> {
                binding.ivAzCheck.isActivated = true
            }
            else -> {
                binding.ivZaCheck.isActivated = true
            }
        }
    }


    override fun initObserver() {

    }

    override fun initListener() {
        binding.rlTime.setOnClickListener {
            sorter.usingTime = true
            sorter.usingPrice = false
            sync()
        }
        binding.rlName.setOnClickListener {
            sorter.usingPrice = true
            sorter.usingTime = false
            sync()
        }
        binding.rlNewToOld.setOnClickListener {
            sorter.timeFilter = Sorter.TimeFilter.NEW_TO_OLDEST
            sync()
        }
        binding.rlOldtoNew.setOnClickListener {
            sorter.timeFilter = Sorter.TimeFilter.OLDEST_TO_NEW
            sync()
        }
        binding.rlPrice.setOnClickListener {
            sorter.nameFilter = Sorter.NameFilter.A_Z
            sync()
        }
        binding.rlZa.setOnClickListener {
            sorter.nameFilter = Sorter.NameFilter.Z_A
            sync()
        }

        binding.btnClose.onAvoidDoubleClick {
            dismiss()
        }

        binding.btnNext.onAvoidDoubleClick {
            SharedPref.sorterLocal = sorter
            listener?.filterCall(sorter)
            dismiss()
        }
    }

    interface FilterListener {
        fun filterCall(sorter: Sorter)
    }

}