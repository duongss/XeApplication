package com.dss.xeapplication.base

import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.greenrobot.eventbus.EventBus

abstract class BaseBottomSheetDialogFragment<T : ViewBinding> : BottomSheetDialogFragment(),
    BaseInit<T> {

    protected lateinit var binding: T

    open var hasEventBus = false

    open var viewAbout: ViewGroup? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (hasEventBus) EventBus.getDefault().register(this)
        initConfig(savedInstanceState)
        initObserver()
        initListener()
    }

    open fun auto(view: ViewGroup) {
        try {
            viewAbout = view
            val displayMetrics = DisplayMetrics()
            requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenHeight = displayMetrics.heightPixels
            val screenWidth = displayMetrics.widthPixels

            // Tự động cuộn BottomSheetDialogFragment khi xoay màn hình
            dialog?.setOnShowListener {
                viewAbout?.let { layout ->
                    val layoutParams = layout.layoutParams as ViewGroup.LayoutParams
                    layoutParams.height =
                        screenHeight / 2 // Thay đổi chiều cao BottomSheetDialogFragment tùy theo nhu cầu
                    layout.layoutParams = layoutParams
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        viewAbout?.let { auto(it) }
    }

    override fun onDestroyView() {
        release()
        if (hasEventBus) EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    open fun release() {}
}