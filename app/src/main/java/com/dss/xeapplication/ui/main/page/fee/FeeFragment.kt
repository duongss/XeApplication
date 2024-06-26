package com.dss.xeapplication.ui.main.page.fee

import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.ListPopupWindow
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseFragment
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
            it.registration + it.registrationCertificate + it.licensePlate + it.civilLiabilityInsurance + it.registration + it.roadMaintenance
        binding.tvSum.text =
            getString(R.string.price_sum, sum.toCurrencyFormat())
        binding.spinnerLocation.setSelection(pos)
        binding.spinnerLocation.dismiss()
    }

    override fun initListener() {
        backListener(binding.toolbar.btnBack) {
            removeSelf()
        }

        binding.btnDone.onAvoidDoubleClick {
            removeSelf()
        }
    }

    private fun AppCompatSpinner.dismiss() {
        val popup = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val listPopupWindow = popup.get(this) as ListPopupWindow
        listPopupWindow.dismiss()
    }
}