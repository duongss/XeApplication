package com.dss.xeapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.component.DialogLoading
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    open val binding by lazy { bindingView() }

    private var saveInstanceStateCalled: Boolean = false

    open val hasEventBus = false

    protected lateinit var dialogLoading: DialogLoading

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        extractData(savedInstanceState)
        if (hasEventBus) EventBus.getDefault().register(this)
        dialogLoading = DialogLoading(requireContext())
        initConfig()
        initObserver()
        initListener()
        initTask()
    }

    override fun onDestroyView() {
        release()
        NativeManager.clear(javaClass.simpleName)
        dialogLoading.dismissDialog()
        if (hasEventBus) EventBus.getDefault().unregister(this)
        super.onDestroyView()
    }

    open fun extractData(savedInstanceState: Bundle?) {}

    open fun initObserver() {}

    open fun initConfig() {}

    open fun initListener() {}

    open fun initTask() {}

    open fun release() {}

    abstract fun bindingView(): T


    override fun onPause() {
        super.onPause()
        saveInstanceStateCalled = true
    }

    override fun onResume() {
        super.onResume()
        saveInstanceStateCalled = false
    }

    fun canChangeFragmentManagerState(): Boolean {
        return !(saveInstanceStateCalled)
    }

    protected fun backListener(
        viewBack: View? = null,
        func: () -> Unit
    ) {
        viewBack?.setOnClickListener {
            func()
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    func()
                }
            })
    }
}