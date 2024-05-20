package com.dss.xeapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.base.extension.gone
import com.dss.xeapplication.databinding.ItemContentNotificationBinding
import com.dss.xeapplication.databinding.ItemSettingBinding
import com.dss.xeapplication.model.Notification
import com.dss.xeapplication.model.SettingP

class AdapterNotification() :
    BaseRecyclerViewAdapter<Notification, ItemContentNotificationBinding>(isItemClick = true) {

    override fun providesItemViewBinding(parent: ViewGroup, viewType: Int) =
        ItemContentNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindData(
        binding: ItemContentNotificationBinding,
        data: Notification,
        position: Int,
        context: Context
    ) {
        if (data.isShow){
            binding.tvContent.text = data.content
        }else{
            binding.root.gone()
        }

    }
}