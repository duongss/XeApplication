package com.dss.xeapplication.ui.notification

import com.dss.xeapplication.base.BaseFragment
import com.dss.xeapplication.base.extension.removeSelf
import com.dss.xeapplication.data.FirebaseStorage
import com.dss.xeapplication.databinding.FragmentNotificationBinding
import com.dss.xeapplication.databinding.FragmentSearchBinding
import com.dss.xeapplication.ui.adapter.AdapterCar
import com.dss.xeapplication.ui.adapter.AdapterNotification
import com.google.android.gms.auth.api.signin.internal.Storage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : BaseFragment<FragmentNotificationBinding>() {

    override fun bindingView() = FragmentNotificationBinding.inflate(layoutInflater)

    private lateinit var adapter: AdapterNotification


    companion object {
        fun newInstance() = NotificationFragment()
    }

    override fun initConfig() {
        super.initConfig()
        initAdapter()
    }

    override fun initListener() {
        super.initListener()

        backListener(binding.ivClose) {
            removeSelf()
        }
    }

    private fun initAdapter() {

        adapter = AdapterNotification()
        adapter.set(FirebaseStorage.listNotification)
        binding.rcv.adapter = adapter
    }

}