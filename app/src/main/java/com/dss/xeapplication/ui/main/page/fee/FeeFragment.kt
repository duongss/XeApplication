package com.dss.xeapplication.ui.main.page.fee

import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.ListPopupWindow
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.base.extension.toCurrencyFormat
import com.dss.xeapplication.databinding.FragmentFeeBinding
import com.dss.xeapplication.model.CarFee
import com.dss.xeapplication.model.LocationFee
import com.dss.xeapplication.ui.diaglog.FeeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeeFragment : BaseFragment<FragmentFeeBinding>() {

    override fun bindingView() = FragmentFeeBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): FeeFragment {
            return FeeFragment()

        }
    }

    override fun initConfig() {
        super.initConfig()
        binding.toolbar.title.text = getString(R.string.data_with_tax)
        binding.toolbar.btnBack.gone()
        val adapter = FeeAdapter(requireContext(), LocationFee.listLocationFee) { it, pos ->
            handleFee(it, pos)
        }

        binding.spinnerLocation.adapter = adapter
        handleFee(LocationFee.listLocationFee[0], 0)

        NativeManager.bindNativeAds(
            this,
            binding.layoutAds,
            R.layout.native_ads_template
        )

    }

    private fun handleFee(it: CarFee, pos: Int) {
        binding.tvRegistration.text = it.registration.toCurrencyFormat()
        binding.tvRegistrationCertificate.text = it.registrationCertificate.toCurrencyFormat()
        binding.tvLicensePlate.text = it.licensePlate.toCurrencyFormat()
        binding.tvCivilLiabilityInsurance.text = it.civilLiabilityInsurance.toCurrencyFormat()
        binding.tvRegistration.text = it.registration.toCurrencyFormat()
        binding.tvRoadMaintenance.text = it.roadMaintenance.toCurrencyFormat()
        binding.tvUnit.text = it.unitPrice

        val sum =
            it.registration + it.licensePlate + it.registrationCertificate + it.roadMaintenance + it.civilLiabilityInsurance
        binding.tvSum.text =
            getString(R.string.price_sum, sum.toCurrencyFormat())
        binding.spinnerLocation.setSelection(pos)
        binding.spinnerLocation.dismiss()
    }

    override fun initListener() {
        binding.btnDone.onAvoidDoubleClick {
            removeSelf()
        }

        binding.lnSpinner.onAvoidDoubleClick {
            binding.spinnerLocation.performClick()
        }
    }

    private fun AppCompatSpinner.dismiss() {
        val popup = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val listPopupWindow = popup.get(this) as ListPopupWindow
        listPopupWindow.dismiss()
    }
}