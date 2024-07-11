package com.dss.xeapplication.ui.main.page.selection

import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.databinding.ChipfilterBinding
import com.dss.xeapplication.databinding.FragmentSelectionBinding
import com.google.android.material.chip.Chip
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
        binding.toolbar.title.text = getString(R.string.selection)
        binding.toolbar.btnBack.gone()

        initChipFuel()
        initChipConvenient()
        initChipSeat()
    }

    private fun initChipConvenient() {
        listOf(R.string.low,R.string.medium,R.string.high).forEach {
            val chip = createChip(it)
            binding.chipGroupEngine.addView(chip)
        }
    }
    private fun initChipSeat() {
        FirebaseStorage.listCar.map { it.numOfSeats }.toSet().forEach {
            val chip = createChip(it.toString())
            binding.chipGroupSeat.addView(chip)
        }
    }

    private fun initChipFuel() {
        listOf(R.string.oil_or_gas,R.string.electric,R.string.hybrid).forEach {
            val chip = createChip(it)
            binding.chipGroupFuel.addView(chip)
        }
    }

    private fun createChip(t:Int): Chip {
       return createChip(getString(t))
    }

    private fun createChip(str:String) : Chip {
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

        }
    }
}