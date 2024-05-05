package com.dss.xeapplication.ui.compare

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseBottomSheetDialogFragment
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.databinding.BottomDialogComparePreBinding
import com.dss.xeapplication.ui.diaglog.UnlockRewardDialog
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComparePreBottomDialog : BaseBottomSheetDialogFragment<BottomDialogComparePreBinding>() {

    override fun bindingView() = BottomDialogComparePreBinding.inflate(layoutInflater)

    private val viewModel by activityViewModels<MainViewModel>()

    companion object {

        fun newInstance(): ComparePreBottomDialog {
            return ComparePreBottomDialog()
        }

    }

    private var listener: PreCompareListener? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            val fragment = requireParentFragment()
            if (fragment is PreCompareListener) {
                listener = fragment
            }
        } catch (e: Exception) {
            val activity = requireActivity()
            if (activity is PreCompareListener) {
                listener = activity
            }
        }
    }

    override fun initConfig(savedInstanceState: Bundle?) {
    }


    override fun initObserver() {
        viewModel.compareCarData.observe(viewLifecycleOwner) {
            it.car1?.let {
                Glide.with(this).load(it.imageCar).into(binding.ivCar1)
                binding.tvNameCar1.text = buildString {
                    append(it.name)
                    append(" ")
                    append(it.model)
                }
            }?:let {
                Glide.with(this).load(R.drawable.ic_add).into(binding.ivCar1)
                binding.tvNameCar1.text = getString(R.string.inSelect)
            }

            it.car2?.let {
                Glide.with(this).load(it.imageCar).into(binding.ivCar2)
                binding.tvNameCar2.text =  buildString {
                    append(it.name)
                    append(" ")
                    append(it.model)
                }
            }?:let {
                Glide.with(this).load(R.drawable.ic_add).into(binding.ivCar2)
                binding.tvNameCar2.text = getString(R.string.inSelect)
            }
        }
    }

    override fun initListener() {

        binding.rlCar1.onAvoidDoubleClick {
            viewModel.updateStateCompare(MainViewModel.STATE_PICK_COMPARE_CAR_1)
            dismiss()
        }

        binding.rlCar2.onAvoidDoubleClick {
            viewModel.updateStateCompare(MainViewModel.STATE_PICK_COMPARE_CAR_2)
            dismiss()
        }

        binding.btnClose.onAvoidDoubleClick {
            viewModel.updateStateCompare(MainViewModel.STATE_CLOSE_PICK_COMPARE)
            dismiss()
        }

        binding.btnNext.onAvoidDoubleClick {
            listener?.onNext()
            dismiss()
        }
    }

    interface PreCompareListener {
        fun onNext()
    }

}