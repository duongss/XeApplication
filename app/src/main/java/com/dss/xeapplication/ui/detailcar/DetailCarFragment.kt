package com.dss.xeapplication.ui.detailcar

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.databinding.FragmentDetailCarBinding
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.model.Car
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

    private lateinit var adapterCar: AdapterCar

    private lateinit var carBundle :Car

    private val viewModel by viewModels<DetailCarViewModel>()

    override fun initConfig() {
        super.initConfig()
        binding.lottieGear.setAnimation("gear_anim.json")
        binding.lottieGear.playAnimation()

        carBundle = arguments?.getParcelable(BUNDLE_DATA)!!
        viewModel.initCar(carBundle)
    }

    private fun initAdapterCar() {
        adapterCar =
            AdapterCar(onItemSelect = { car: Car, i: Int ->

            }, onItemMark = { car: Car, i: Int ->

            })
//        binding.rcvCar.adapter = adapterCar
    }

    override fun initObserver() {
        super.initObserver()

    }

    override fun initListener() {
        super.initListener()

    }

}