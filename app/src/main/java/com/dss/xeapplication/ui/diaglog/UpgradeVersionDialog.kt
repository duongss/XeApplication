package com.dss.xeapplication.ui.diaglog

import android.content.Context
import com.dss.xeapplication.base.BaseDialogFragment
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.databinding.DialogUpgradeVersionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpgradeVersionDialog : BaseDialogFragment<DialogUpgradeVersionBinding>() {

    override fun bindingView(): DialogUpgradeVersionBinding =
        DialogUpgradeVersionBinding.inflate(layoutInflater)

    companion object {
        fun newInstance(): UpgradeVersionDialog {
            return UpgradeVersionDialog()

        }
    }

    private var listener: OnUpdateAppListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as OnUpdateAppListener

        } catch (e:Exception) {
            try {
                listener = parentFragment as OnUpdateAppListener
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    override fun initListener() {

        binding.btnUpgrade.onAvoidDoubleClick {
            listener?.onUpdateApp()
            dismiss()
        }

        binding.btnLater.onAvoidDoubleClick {
            dismiss()
        }
    }

    interface OnUpdateAppListener {
        fun onUpdateApp()
    }
}