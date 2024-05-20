package com.dss.xeapplication.ui.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.inter.InterstitialManager
import com.dss.xeapplication.base.ads.inter.OnCompletedListener
import com.dss.xeapplication.base.extension.addFragment
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.hideSoftKeyboard
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.base.extension.showSoftKeyboard
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.FragmentSearchBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.ui.detailcar.DetailCarFragment
import com.dss.xeapplication.ui.main.page.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    override fun bindingView() = FragmentSearchBinding.inflate(layoutInflater)

    private val viewModel: SearchViewModel by viewModels()

    private lateinit var adapter: AdapterCar

    private lateinit var closeSearchImageView: ImageView

    private lateinit var searchEdit: EditText

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun initConfig() {
        super.initConfig()
        initSearch()
        initAdapterFeature()
    }

    private fun initSearch() {
        searchEdit =
            binding.layoutSearch.findViewById(androidx.appcompat.R.id.search_src_text)
        closeSearchImageView =
            binding.layoutSearch.findViewById(androidx.appcompat.R.id.search_close_btn)
        searchEdit.textSize = resources.getDimension(R.dimen.size_small)
        searchEdit.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.layoutSearch.onActionViewExpanded()
        searchEdit.performClick()
        binding.layoutSearch.setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                Handler(Looper.getMainLooper()).postDelayed({
                    requireActivity().showSoftKeyboard(searchEdit)
                }, 500)
            }
        }

    }

    override fun initObserver() {
        super.initObserver()

        viewModel.dataCars.observe(this){
            adapter.set(it)
        }
    }

    override fun initListener() {
        super.initListener()

        closeSearchImageView.setOnClickListener {
            searchEdit.setText("")
        }

        binding.layoutSearch.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (!p0.isNullOrEmpty()) {
                    binding.rcv.visible()
                    viewModel.filter(p0)
                } else {
                    binding.rcv.gone()
                }
                return false
            }
        })

        backListener(binding.ivClose) {
            requireActivity().hideSoftKeyboard(searchEdit)
            removeSelf()
        }
    }

    private fun initAdapterFeature() {
        adapter =
            AdapterCar(onItemSelect = { car: Car, i: Int ->
                requireActivity().hideSoftKeyboard(searchEdit)
                InterstitialManager.show(requireActivity(), object : OnCompletedListener {
                    override fun onCompleted() {
                        addFragment(DetailCarFragment.newInstance(car))
                    }
                })
            }, onItemMark = { car: Car, i: Int ->
                viewModel.updateMark(car)
            })
        binding.rcv.adapter = adapter
    }

}