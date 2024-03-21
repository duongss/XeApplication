package com.dss.xeapplication.feature.main.page.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.dss.xeapplication.MainActivity
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.data.BrandProvider
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.databinding.FragmentHomeBinding
import com.dss.xeapplication.feature.adapter.AdapterBrand
import com.dss.xeapplication.feature.adapter.AdapterCar
import com.dss.xeapplication.feature.main.viewmodel.MainViewModel
import com.google.android.gms.auth.api.signin.internal.Storage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun bindingView() = FragmentHomeBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = HomeFragment()

    }

    private lateinit var adapterCar : AdapterCar
    private lateinit var adapterBrand : AdapterBrand

    private val viewModel by viewModels<HomeViewModel>()

    override fun initConfig() {
        super.initConfig()
        initAdapterCar()
        initAdapterBrand()
        binding.tvLocation.text = getString(R.string.viet_nam)


    }

    private fun initAdapterCar() {
        adapterCar = AdapterCar()
        binding.rcvCar.adapter = adapterCar
    }

    private fun initAdapterBrand() {
        adapterBrand = AdapterBrand()
        binding.rcvBrand.adapter = adapterBrand
    }

    override fun initObserver() {
        super.initObserver()

        viewModel.dataCars.observe(this){
            adapterCar.set(it)
            adapterBrand.set(BrandProvider.listBrand )
        }
    }
}