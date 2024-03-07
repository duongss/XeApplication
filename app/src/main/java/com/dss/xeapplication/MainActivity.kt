package com.dss.xeapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.base.extension.replaceFragment
import com.dss.xeapplication.databinding.ActivityMainBinding
import com.dss.xeapplication.feature.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : BaseActivity<ActivityMainBinding>() {
    override fun bindingView() = ActivityMainBinding.inflate(layoutInflater)

    companion object {
        fun newIntent(
            context: Context
        ): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun initConfig(savedInstanceState: Bundle?) {
        super.initConfig(savedInstanceState)
        replaceFragment(MainFragment.newInstance(), backStack = false, isAnim = false)
    }

}