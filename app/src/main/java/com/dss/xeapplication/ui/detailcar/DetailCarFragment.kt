package com.dss.xeapplication.ui.detailcar

import android.os.Handler
import android.os.Looper
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.extension.hideSoftKeyboard
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.databinding.FragmentDetailCarBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.ui.adapter.AdapterSpec
import com.dss.xeapplication.ui.diaglog.RateUsDialog
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


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


        NativeManager.bindNativeAds(
            this@DetailCarFragment,
            binding.layoutAds,
            R.layout.native_ads_template_2
        )

        lifecycleScope.launchWhenResumed {
            delay(4000)


            if (!SharedPref.isRate) {
                showChildDialog(RateUsDialog.newInstance())
            }
        }

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