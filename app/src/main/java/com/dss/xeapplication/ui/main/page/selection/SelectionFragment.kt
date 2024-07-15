package com.dss.xeapplication.ui.main.page.selection

import androidx.fragment.app.activityViewModels
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.ChipfilterBinding
import com.dss.xeapplication.databinding.FragmentSelectionBinding
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.google.android.material.chip.Chip
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

        initChipFuel()
        initChipConvenient()
        initChipSeat()
        initChipBottom()
    }

    private fun initChipConvenient() {
        viewModel.listConvenient.forEach {
            val chip = createChip(it)
            binding.chipGroupConvenient.addView(chip)
        }
    }

    private fun initChipSeat() {
        viewModel.listSeat.forEach {
            val chip = createChip(it.toString())
            binding.chipGroupSeat.addView(chip)
        }
    }

    private fun initChipFuel() {
        viewModel.listTypeFuel.forEach {
            val chip = createChip(it)
            binding.chipGroupFuel.addView(chip)
        }
    }

    private fun initChipBottom() {
        viewModel.listBottomType.forEach {
            val chip = createChip(it)
            binding.chipBottom.addView(chip)
        }
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
        }
        return chip
    }

    override fun initListener() {
        backListener(binding.toolbar.btnBack) {
            removeSelf()
        }

        binding.btnSet.onAvoidDoubleClick {
            viewModel.thinkData(
                binding.chipGroupConvenient.checkedChipId,
                binding.chipGroupFuel.checkedChipId,
                binding.chipGroupSeat.checkedChipId,
                binding.chipBottom.checkedChipId,
                binding.edtPrice.text.toString()
            )
        }
    }
}