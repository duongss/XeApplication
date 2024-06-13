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
import com.dss.xeapplication.base.extension.invisible
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.showChildDialog
import com.dss.xeapplication.base.extension.toastMsg
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.data.FirebaseStorage.listNotification
import com.dss.xeapplication.databinding.FragmentHomeBinding
import com.dss.xeapplication.model.BrandProvider
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.Sorter
import com.dss.xeapplication.ui.adapter.AdapterBrand
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.ui.compare.CompareFragment
import com.dss.xeapplication.ui.compare.ComparePreBottomDialog
import com.dss.xeapplication.ui.detailcar.DetailCarFragment
import com.dss.xeapplication.ui.detailcar.FeeFragment
import com.dss.xeapplication.ui.diaglog.FilterBottomDialog
import com.dss.xeapplication.ui.diaglog.UnlockRewardDialog
import com.dss.xeapplication.ui.main.viewmodel.MainViewModel
import com.dss.xeapplication.ui.notification.NotificationFragment
import com.dss.xeapplication.ui.search.SearchFragment
import com.wavez.p27_pdf_scanner.data.local.SharedPref
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), FilterBottomDialog.FilterListener,
    ComparePreBottomDialog.PreCompareListener, UnlockRewardDialog.UnlockForFreeListener {

    override fun bindingView() = FragmentHomeBinding.inflate(layoutInflater)

    companion object {
        const val LIST_FAV = 1
        const val LIST_ALL = 2

        fun newInstance() = HomeFragment()

    }

    private lateinit var adapterCar: AdapterCar
    private lateinit var adapterBrand: AdapterBrand

    private val viewModel by viewModels<HomeViewModel>()
    private val activityViewModel by activityViewModels<MainViewModel>()

    private var yList = 0
    private var listMode = LIST_ALL

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
                if (activityViewModel.stateCompare.value == MainViewModel.STATE_CLOSE_PICK_COMPARE) {
                    InterstitialManager.show(requireActivity(), object : OnCompletedListener {
                        override fun onCompleted() {
                            addFragment(DetailCarFragment.newInstance(car))
                        }
                    })
                } else {
                    activityViewModel.compareCarData.value?.let {
                        when (activityViewModel.stateCompare.value) {
                            MainViewModel.STATE_PICK_COMPARE_CAR_1 -> {
                                activityViewModel.updateCar1(car)

                            }

                            MainViewModel.STATE_PICK_COMPARE_CAR_2 -> {
                                activityViewModel.updateCar2(car)
                            }
                        }

                        adapterCar.syncSelected(it)
                        if (it.car1 != null && it.car2 != null) {
                            showChildDialog(ComparePreBottomDialog.newInstance())
                        }
                    }
                }

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


            if (FirebaseStorage.listCar.isEmpty()) {
                binding.lnEmpty.visible()
                binding.coordinator.invisible()
            } else {
                binding.coordinator.visible()
                binding.lnEmpty.gone()
            }
        }

        activityViewModel.stateCompare.observe(requireActivity()) {
            when (it) {
                MainViewModel.STATE_CLOSE_PICK_COMPARE -> {
                    adapterCar.inSelected(false)
                    binding.cslGroup.visible()
                    binding.lnGroupCompare.gone()
                }

                MainViewModel.STATE_PICK_COMPARE_CAR_1 -> {
                    adapterCar.inSelected(true)
                    binding.cslGroup.invisible()
                    binding.lnGroupCompare.visible()
                }

                MainViewModel.STATE_PICK_COMPARE_CAR_2 -> {
                    adapterCar.inSelected(true)
                    binding.cslGroup.invisible()
                    binding.lnGroupCompare.visible()
                }
            }
        }
    }

    override fun initListener() {
        super.initListener()

        binding.ivSort.onAvoidDoubleClick {
            showChildDialog(FilterBottomDialog.newInstance())
        }

        binding.btnFav.onAvoidDoubleClick {
            if (listMode == LIST_ALL) {
                binding.btnFav.text = getString(R.string.danh_sach_luu)
                listMode = LIST_FAV
                adapterCar.set(FirebaseStorage.markList)

                if (FirebaseStorage.markList.isEmpty()) {
                    binding.lnEmptyList.visible()
                } else {
                    binding.lnEmptyList.gone()
                }
            } else {
                binding.btnFav.text = getString(R.string.danh_sach_xe)
                viewModel.initDataCars()
                listMode = LIST_ALL
                binding.lnEmptyList.gone()
            }

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
            addFragment(SearchFragment.newInstance())
        }

        binding.btnTop.setOnClickListener {
            binding.rcvCar.smoothScrollToPosition(0)
            binding.btnTop.gone()
        }

        binding.btnExitCompare.onAvoidDoubleClick {
            activityViewModel.updateStateCompare(MainViewModel.STATE_CLOSE_PICK_COMPARE)
        }

        binding.ivNotification.onAvoidDoubleClick {
            if (listNotification.isEmpty()) {
                toastMsg(R.string.no_notification)
                return@onAvoidDoubleClick
            }
            addFragment(NotificationFragment.newInstance())
        }

        binding.btnFee.onAvoidDoubleClick {
            addFragment(FeeFragment.newInstance())
        }

        binding.tvLocation.onAvoidDoubleClick {
            toastMsg(R.string.comming_soon)
        }
    }

    override fun filterCall(sorter: Sorter) {
        viewModel.initDataCars()
    }

    override fun onNext() {
        if (SharedPref.isVip) {
            onUnlockedFromUser()
        } else {
            showChildDialog(UnlockRewardDialog.newInstance())
        }
    }

    override fun onUnlockedFromUser() {
        activityViewModel.createListCompare()
        binding.btnExitCompare.performClick()
        addFragment(CompareFragment.newInstance())
    }
}