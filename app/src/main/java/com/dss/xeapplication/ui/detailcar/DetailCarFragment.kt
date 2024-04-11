package com.dss.xeapplication.ui.detailcar

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentDetailCarBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.createListSpecifications
import com.dss.xeapplication.ui.adapter.AdapterSpec
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailCarFragment : BaseFragment<FragmentDetailCarBinding>() {

    override fun bindingView() = FragmentDetailCarBinding.inflate(layoutInflater)

    companion object {

        const val BUNDLE_DATA = "bundle_data"
        fun newInstance(car: Car) = DetailCarFragment().apply {
            arguments = bundleOf(BUNDLE_DATA to car)
        }

    }

    private lateinit var adapterCar: AdapterSpec

    private lateinit var carBundle: Car

    private val viewModel by viewModels<DetailCarViewModel>()

    override fun initConfig() {
        super.initConfig()
        binding.lottieGear.setAnimation("gear_anim.json")
        binding.lottieGear.playAnimation()
        carBundle = arguments?.getParcelable(BUNDLE_DATA)!!

        carBundle.apply {
            Glide.with(this@DetailCarFragment).load(imageCar).into(binding.ivCar)
            binding.tvName.text = buildString {
                append(name)
                append("-")
                append(model)
            }
            binding.tvBrand.text = brand
            binding.ivBookMark.isActivated = isMark
        }


        initAdapterCar()
    }

    private fun initAdapterCar() {
        adapterCar = AdapterSpec(carBundle.createListSpecifications())
        binding.rcvData.adapter = adapterCar
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()

        binding.ivBookMark.setOnClickListener {
            carBundle.isMark = !carBundle.isMark
            binding.ivBookMark.isActivated = carBundle.isMark

            viewModel.updateMark(carBundle)
        }

        backListener(binding.ivBack){
            removeSelf()
        }
    }

}