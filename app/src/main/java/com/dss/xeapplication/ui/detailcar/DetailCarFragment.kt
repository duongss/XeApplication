package com.dss.xeapplication.ui.detailcar

import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.hideSoftKeyboard
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.base.extension.showSoftKeyboard
import com.dss.xeapplication.databinding.FragmentDetailCarBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.ui.adapter.AdapterSpec
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
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

    private val viewModel by activityViewModels<MainViewModel>()

    override fun initConfig() {
        super.initConfig()
        Handler(Looper.getMainLooper()).postDelayed({
            hideSoftKeyboard()
        }, 500)
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
            binding.tvPrice.text = currentPrice
            binding.ivBookMark.isActivated = isMark
        }


        initAdapterCar()
    }

    private fun initAdapterCar() {
        viewModel.createListDetail(carBundle){
            adapterCar = AdapterSpec(it)
            binding.rcvData.adapter = adapterCar
        }

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