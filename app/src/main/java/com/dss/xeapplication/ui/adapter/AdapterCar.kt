package com.dss.xeapplication.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.dss.xeapplication.base.BaseRecyclerViewAdsAdapter
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.databinding.ItemCarBinding
import com.dss.xeapplication.model.Car
import com.dss.xeapplication.model.CompareCarData

class AdapterCar(var onItemSelect: (Car, Int) -> Unit, var onItemMark: (Car, Int) -> Unit) :
    BaseRecyclerViewAdsAdapter<Car, ItemCarBinding>() {

    private var isShowSelected = false

    override fun providesItemViewBinding(parent: ViewGroup, viewType: Int) =
        ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    override fun bindData(binding: ItemCarBinding, data: Car?, position: Int, context: Context) {
        data?.let {
            Glide.with(context).load(data.imageCar).into(binding.ivCar)
            binding.tvName.text = buildString {
                append(data.name)
                append("    ")
                append(data.model)
            }
            if (isShowSelected) {
                binding.root.isSelected = data.isSelected
            } else {
                binding.root.isSelected = false
            }
            binding.tvPrice.text = data.currentPrice
            binding.tvNumberChair.text = data.numOfSeats.toString()
            binding.tvBrand.text = data.brand

            binding.ivBookMark.isActivated = data.isMark
            binding.root.onAvoidDoubleClick {
                onItemSelect(data, position)
            }

            binding.ivBookMark.onAvoidDoubleClick {
                data.isMark = !data.isMark
                onItemMark(data, position)
                notifyItemChanged(position)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun inSelected(isShow :Boolean = false) {
        isShowSelected = isShow
        notifyDataSetChanged()
    }

    fun syncSelected(c: CompareCarData) {
        dataList.forEachIndexed { index, data ->
            data.t?.let { car ->
                if (c.car1?.id == car.id) {
                    car.isSelected = true
                } else if (c.car2?.id == car.id) {
                    car.isSelected = true
                } else {
                    car.isSelected = false
                }

            }
         }
        notifyDataSetChanged()
    }
}