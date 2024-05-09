package com.dss.xeapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dss.xeapplication.databinding.ItemCompareBooleanBinding
import com.dss.xeapplication.databinding.ItemCompareContentBinding
import com.dss.xeapplication.databinding.ItemCompareIntBinding
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT_BOOLEAN
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT_INT
import com.dss.xeapplication.model.SpecificationsCompareCar

class AdapterCompare() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class Item1ViewHolder(val binding: ItemCompareContentBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class Item2ViewHolder(val binding: ItemCompareBooleanBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class Item3ViewHolder(val binding: ItemCompareIntBinding) :
        RecyclerView.ViewHolder(binding.root)

    var dataList: ArrayList<SpecificationsCompareCar> = arrayListOf()

    fun set(dataList: List<SpecificationsCompareCar>) {
        val clone: List<SpecificationsCompareCar> = ArrayList(dataList)
        this.dataList.clear()
        this.dataList.addAll(clone)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CONTENT_BOOLEAN -> Item2ViewHolder(
                ItemCompareBooleanBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            TYPE_CONTENT_INT -> Item3ViewHolder(
                ItemCompareIntBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> {
                Item1ViewHolder(
                    ItemCompareContentBinding.inflate(
                        LayoutInflater.from(
                            parent.context
                        ), parent, false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]

        when (getItemViewType(position)) {
            TYPE_CONTENT_BOOLEAN -> {
                val vh = holder as Item2ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.ivCheck1.isActivated = data.contentBooleanCar1
                vh.binding.ivCheck2.isActivated = data.contentBooleanCar2
            }

            TYPE_CONTENT_INT -> {
                val vh = holder as Item3ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.tvContent1.text = buildString {
                    append(data.contentIntCar1.toString())
                    append(" ")
                    append(data.unit)
                }
                vh.binding.tvContent2.text = buildString {
                    append(data.contentIntCar2.toString())
                    append(" ")
                    append(data.unit)
                }
            }

            TYPE_CONTENT -> {
                val vh = holder as Item1ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.tvContent1.text = data.contentCar1
                vh.binding.tvContent2.text = data.contentCar2
            }
        }
    }

}