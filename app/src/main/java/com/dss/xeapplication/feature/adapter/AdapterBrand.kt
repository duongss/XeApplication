package com.dss.xeapplication.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.databinding.ItemBrandBinding
import com.dss.xeapplication.databinding.ItemCarBinding
import com.dss.xeapplication.model.Brand
import com.dss.xeapplication.model.Car

class AdapterBrand :
    BaseRecyclerViewAdapter<Brand, ItemBrandBinding>(isItemClick = true) {

    override fun providesItemViewBinding(parent: ViewGroup, viewType: Int) =
        ItemBrandBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindData(
        binding: ItemBrandBinding,
        data: Brand,
        position: Int,
        context: Context
    ) {
        Glide.with(context).load(data.image).into(binding.ivBrand)
        binding.tvBrandName.text = buildString {
            append(data.name)
        }
    }
}