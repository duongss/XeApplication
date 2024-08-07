package com.dss.xeapplication.base

import android.content.Context
import android.content.ContextWrapper
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dss.xeapplication.App
import com.dss.xeapplication.base.ads.inter.InterstitialManager
import com.dss.xeapplication.base.ads.nativeads.NativeManager
import com.dss.xeapplication.base.component.ContextUtils
import com.dss.xeapplication.base.component.DialogLoading
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {

    open val binding by lazy { bindingView() }

    private var saveInstanceStateCalled: Boolean = false

    lateinit var dialogLoading: DialogLoading

    var primaryBaseActivity: Context? = null

    open val hasEventBus = false


    override fun attachBaseContext(newBase: Context) {
        val localeToSwitchTo = App.instance().locale
        primaryBaseActivity = newBase
        val localeUpdatedContext: ContextWrapper =
            ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingView()
        if (hasEventBus) EventBus.getDefault().register(this)
        dialogLoading = DialogLoading(this)
        InterstitialManager.load(this)
        setContentView(binding.root)
        saveInstanceStateCalled = false
        initConfig(savedInstanceState)
        initObserver()
        initListener()
    }


    override fun onDestroy() {
        NativeManager.clear(javaClass.simpleName)
        if (hasEventBus) EventBus.getDefault().unregister(this)
        release()
        dialogLoading.dismissDialog()
        super.onDestroy()
    }

    open fun initConfig(savedInstanceState: Bundle?) {}

    open fun initListener() {}

    open fun initObserver() {}

    open fun release() {}

    abstract fun bindingView(): T

    override fun onStart() {
        super.onStart()
        saveInstanceStateCalled = false
    }

    override fun onResume() {
        super.onResume()
        saveInstanceStateCalled = false
    }

    fun canChangeFragmentManagerState(): Boolean {
        return !(saveInstanceStateCalled || isFinishing)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceStateCalled = true
    }

    open fun backListener(
        viewBack: View? = null,
        func: () -> Unit
    ) {
        viewBack?.setOnClickListener {
            func()
        }
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    func()
                }
            })
    }

    fun setStatusBarFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

}