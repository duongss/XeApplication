package com.dss.xeapplication.ui.main.page.selection

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.inter.InterstitialManager
import com.dss.xeapplication.base.ads.inter.OnCompletedListener
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.toastMsg
import com.dss.xeapplication.databinding.ChipfilterBinding
import com.dss.xeapplication.databinding.FragmentSelectionBinding
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectionFragment : BaseFragment<FragmentSelectionBinding>() {

    override fun bindingView() = FragmentSelectionBinding.inflate(layoutInflater)

    private val viewModel by activityViewModels<MainViewModel>()

    companion object {
        fun newInstance(): SelectionFragment {
            return SelectionFragment()

        }
    }

    override fun initConfig() {
        super.initConfig()
        binding.toolbar.title.text = getString(R.string.selection)
        binding.toolbar.btnBack.gone()

        initChip(viewModel.listConvenient, binding.chipGroupConvenient)
        initChip(viewModel.listSeat, binding.chipGroupSeat)
        initChip(viewModel.listTypeFuel, binding.chipGroupFuel)
        initChip(viewModel.listBottomType, binding.chipBottom)
    }

    private fun initChip(list: List<Int>, v: ChipGroup) {
        var firstChip: Chip? = null
        list.forEachIndexed { index, it ->
            val chip = try {
                createChip(it)
            } catch (e: Exception) {
                createChip(it.toString())
            }
            if (index == 0) {
                firstChip = chip
            }
            v.addView(chip)
        }
        firstChip?.let { v.check(it.id) }
    }

    private fun createChip(t: Int): Chip {
        return createChip(getString(t))
    }

    private fun createChip(str: String): Chip {
        val chip = ChipfilterBinding.inflate(layoutInflater).root
        chip.apply {
            text = str
            isCloseIconVisible = false
            isCheckedIconVisible = true
            checkedIconTint =
                ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.color3))
        }
        return chip
    }

    override fun initListener() {
        binding.btnSet.onAvoidDoubleClick {
            val selectedConvenient =
                binding.chipGroupConvenient.findViewById<Chip>(binding.chipGroupConvenient.checkedChipId)?.text?.toString()
            val selectedFuel =
                binding.chipGroupFuel.findViewById<Chip>(binding.chipGroupFuel.checkedChipId)?.text?.toString()
            val selectedSeat =
                binding.chipGroupSeat.findViewById<Chip>(binding.chipGroupSeat.checkedChipId)?.text?.toString()
            var seat = 0
            if (!selectedSeat.isNullOrEmpty()) {
                seat = selectedSeat.toInt()
            }
            val selectedBottom =
                binding.chipBottom.findViewById<Chip>(binding.chipBottom.checkedChipId)?.text?.toString()

            if (selectedConvenient.isNullOrEmpty() && selectedFuel.isNullOrEmpty() && seat == 0 && selectedBottom.isNullOrEmpty() && binding.edtPrice.text.isNullOrEmpty()) {
                toastMsg(R.string.no_data_suit)
                return@onAvoidDoubleClick
            }

            viewModel.thinkData(
                requireContext(),
                selectedConvenient ?: "",
                selectedFuel ?: "",
                seat,
                selectedBottom ?: "",
                binding.edtPrice.text.toString(),
            )
            InterstitialManager.show(requireActivity(), object : OnCompletedListener {
                override fun onCompleted() {
                    addFragment(CarOverFragment.newInstance())
                }
            })
        }
    }
}