package com.dss.xeapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.databinding.ItemSettingBinding
import com.dss.xeapplication.model.SettingP

class AdapterSetting() :
    BaseRecyclerViewAdapter<SettingP, ItemSettingBinding>(isItemClick = true) {

    override fun providesItemViewBinding(parent: ViewGroup, viewType: Int) =
        ItemSettingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindData(
        binding: ItemSettingBinding,
        data: SettingP,
        position: Int,
        context: Context
    ) {
        Glide.with(context).load(data.image).into(binding.img)
        binding.tv.text = context.getString(data.name)

    }
}