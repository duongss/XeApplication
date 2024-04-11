package com.dss.xeapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.base.extension.setTint
import com.dss.xeapplication.databinding.ItemCarBinding
import com.dss.xeapplication.model.Car

class AdapterCar(var onItemSelect: (Car, Int) -> Unit,var onItemMark: (Car, Int) -> Unit) :
    BaseRecyclerViewAdapter<Car, ItemCarBinding>() {

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

        binding.ivBookMark.isActivated = data.isMark
        binding.root.onAvoidDoubleClick {
            onItemSelect(data,position)
        }

        binding.ivBookMark.onAvoidDoubleClick {
            data.isMark = !data.isMark
            onItemMark(data,position)
            notifyItemChanged(position)
        }
    }
}