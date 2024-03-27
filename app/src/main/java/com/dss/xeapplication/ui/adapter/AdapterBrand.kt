package com.dss.xeapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.base.extension.invisible
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.ItemBrandBinding
import com.dss.xeapplication.model.Brand

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
        if (data.image.isNotEmpty()){
            Glide.with(context).load(data.image).error(R.drawable.ic_all).into(binding.ivBrand)
        }else{
            Glide.with(context).load(R.drawable.ic_all).into(binding.ivBrand)
        }

        if (data.isSelected){
            binding.ivSelected.visible()
        }else{
            binding.ivSelected.invisible()
        }
        binding.tvBrandName.text = data.name.name
    }
}