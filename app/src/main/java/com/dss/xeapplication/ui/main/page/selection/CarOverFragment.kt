package com.dss.xeapplication.ui.main.page.selection

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.inter.InterstitialManager
import com.dss.xeapplication.base.ads.inter.OnCompletedListener
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.FragmentCarOverBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.ui.detailcar.DetailCarFragment
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarOverFragment : BaseFragment<FragmentCarOverBinding>() {

    override fun bindingView() = FragmentCarOverBinding.inflate(layoutInflater)

    private val viewModel by activityViewModels<MainViewModel>()

    companion object {
        fun newInstance(): CarOverFragment {
            return CarOverFragment()

        }
    }

    private lateinit var adapter: AdapterCar

    override fun initConfig() {
        super.initConfig()
        binding.toolbar.title.text = getString(R.string.car_suitable)

        adapter =
            AdapterCar(
                onItemSelect = { car: Car, i: Int ->
                    InterstitialManager.show(requireActivity(), object : OnCompletedListener {
                        override fun onCompleted() {
                            addFragment(DetailCarFragment.newInstance(car))
                        }
                    })
                },
                onItemMark = { car: Car, i: Int -> viewModel.updateMark(car) })
        binding.rcvCar.adapter = adapter

        NativeManager.getNativeAds(requireActivity(), R.layout.native_ads_template_2, adapter.maxAd)
            ?.let { adapter.setListNative(it) }
    }

    override fun initObserver() {
        super.initObserver()
        lifecycleScope.launchWhenResumed {
            viewModel.listResultSelection.collect { l ->
                if (l.isEmpty()) {
                    binding.lnEmpty.visible()
                    binding.rcvCar.gone()
                } else {
                    binding.lnEmpty.gone()
                    binding.rcvCar.visible()
                }

                adapter.set(l)
            }
        }
    }

    override fun initListener() {
        backListener(binding.toolbar.btnBack) {
            removeSelf()
        }
    }
}