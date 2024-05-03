package com.dss.xeapplication.ui.main.page.home

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.inter.InterstitialManager
import com.dss.xeapplication.base.ads.inter.OnCompletedListener
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.FragmentHomeBinding
import com.dss.xeapplication.model.BrandProvider
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Sorter
import com.dss.xeapplication.ui.adapter.AdapterBrand
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.ui.detailcar.DetailCarFragment
import com.dss.xeapplication.ui.diaglog.FilterBottomDialog
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.dss.xeapplication.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), FilterBottomDialog.FilterListener {

    override fun bindingView() = FragmentHomeBinding.inflate(layoutInflater)

    companion object {
        fun newInstance() = HomeFragment()

    }

    private lateinit var adapterCar: AdapterCar
    private lateinit var adapterBrand: AdapterBrand

    private val viewModel by viewModels<HomeViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    private var yList = 0
    override fun initConfig() {
        super.initConfig()
        initAdapterCar()
        initAdapterBrand()
        binding.tvLocation.text = getString(R.string.viet_nam)
        binding.btnTop.gone()

    }

    private fun initAdapterCar() {
        adapterCar =
            AdapterCar(onItemSelect = { car: Car, i: Int ->
                InterstitialManager.show(requireActivity(), object : OnCompletedListener {
                    override fun onCompleted() {
                        addFragment(DetailCarFragment.newInstance(car))
                    }
                })
            }, onItemMark = { car: Car, i: Int ->
                viewModel.updateMark(car)
            })
        binding.rcvCar.adapter = adapterCar
    }

    private fun initAdapterBrand() {
        adapterBrand = AdapterBrand()
        binding.rcvBrand.adapter = adapterBrand

        adapterBrand.onItemSelectListener = { brand, itemBrandBinding, i ->
            viewModel.initDataCars(brand)
            adapterBrand.dataList.forEach {
                it.isSelected = it == brand
            }
            adapterBrand.notifyItemChanged(i)
        }

    }

    override fun initObserver() {
        super.initObserver()

        viewModel.dataCars.observe(this) {
            adapterCar.set(it)
            adapterBrand.set(BrandProvider.listBrand)
        }
    }

    override fun initListener() {
        super.initListener()

        binding.ivSort.onAvoidDoubleClick {
            showChildDialog(FilterBottomDialog.newInstance())
        }


        binding.rcvCar.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                yList = dy

                if (dy > 0) {
                    activityViewModel.bottomHidden()
                } else if (dy < 0) {
                    activityViewModel.bottomVisible()
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (yList <= 0) {
                        binding.btnTop.gone()
                    } else {
                        yList = 0
                        binding.btnTop.visible()
                    }
                }
            }
        })

        binding.btnSearch.onAvoidDoubleClick {
            startActivity(SearchActivity.newIntent(requireActivity()))
        }

        binding.btnTop.setOnClickListener {
            binding.rcvCar.smoothScrollToPosition(0)
            binding.btnTop.gone()
        }
    }

    override fun filterCall(sorter: Sorter) {
        viewModel.initDataCars()
    }
}