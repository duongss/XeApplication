package com.dss.xeapplication.ui.compare

import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.databinding.FragmentCompareBinding
import com.dss.xeapplication.model.nameWithModel
import com.dss.xeapplication.ui.adapter.AdapterCompare
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CompareFragment : BaseFragment<FragmentCompareBinding>() {

    override fun bindingView() = FragmentCompareBinding.inflate(layoutInflater)

    companion object {

        fun newInstance() = CompareFragment()

    }

    private val viewModel by activityViewModels<MainViewModel>()

    private lateinit var adapter: AdapterCompare

    override fun initConfig() {
        super.initConfig()
        binding.lottieGear.setAnimation("gear_anim.json")
        binding.lottieGear.playAnimation()

        adapter = AdapterCompare()
        binding.rcvData.adapter = adapter

        binding.tvNameCar1.isSelected = true
        binding.tvNameCar2.isSelected = true
    }


    override fun initObserver() {
        super.initObserver()

        viewModel.compareCarData.observe(viewLifecycleOwner) {
            it.car1?.let { car ->
                Glide.with(this).load(car.imageCar).into(binding.ivCarBefore)
                binding.tvPrice1.text = car.currentPrice
                binding.tvNameCar1.text = car.nameWithModel()
            }

            it.car2?.let { car ->
                Glide.with(this).load(car.imageCar).into(binding.ivCarAfter)
                binding.tvPrice2.text = car.currentPrice
                binding.tvNameCar2.text = car.nameWithModel()
            }

            adapter.set(it.listCompare)
        }
    }

    override fun initListener() {
        super.initListener()


        backListener(binding.ivClose) {
            removeSelf()
        }
    }

}