package com.dss.xeapplication.feature.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.databinding.ItemCarBinding
import com.dss.xeapplication.model.Car

class AdapterCar :
    BaseRecyclerViewAdapter<Car, ItemCarBinding>(isItemClick = true) {

    override fun providesItemViewBinding(parent: ViewGroup, viewType: Int) =
        ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindData(
        binding: ItemCarBinding,
        data: Car,
        position: Int,
        context: Context
    ) {
        Glide.with(context).load(data.imageCar).into(binding.ivCar)
        binding.tvName.text = buildString {
            append(data.name)
            append("    ")
            append(data.model)
        }
        binding.tvPrice.text = data.currentPrice
        binding.tvNumberChair.text = data.numOfSeats.toString()
        binding.tvBrand.text = data.brand
    }
}