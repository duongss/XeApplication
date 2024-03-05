package com.dss.xeapplication

import com.dss.xeapplication.base.BaseActivity
import com.dss.xeapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : BaseActivity<ActivityMainBinding>() {
    override fun bindingView() = ActivityMainBinding.inflate(layoutInflater)
}