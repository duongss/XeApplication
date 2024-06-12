package com.dss.xeapplication.ui.diaglog

import android.content.Context
import com.dss.xeapplication.base.BaseDialogFragment
import com.dss.xeapplication.databinding.DialogTaxBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaxDialog : BaseDialogFragment<DialogTaxBinding>() {

    override fun bindingView(): DialogTaxBinding =
        DialogTaxBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): TaxDialog {
            return TaxDialog()

        }
    }

    private var listener: OnUpdateAppListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnUpdateAppListener

        } catch (e: Exception) {
            try {
                listener = parentFragment as OnUpdateAppListener
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun initListener() {

    }

    interface OnUpdateAppListener {
        fun onUpdateApp()
    }
}