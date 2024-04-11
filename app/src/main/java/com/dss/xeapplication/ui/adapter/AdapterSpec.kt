package com.dss.xeapplication.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dss.xeapplication.databinding.ItemBooleanSpecBinding
import com.dss.xeapplication.databinding.ItemContentIntBinding
import com.dss.xeapplication.databinding.ItemContentSpecBinding
import com.dss.xeapplication.model.SpecificationsCar
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT_BOOLEAN
import com.dss.xeapplication.model.SpecificationsCar.Companion.TYPE_CONTENT_INT

class AdapterSpec(var dataList: ArrayList<SpecificationsCar>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class Item1ViewHolder(val binding: ItemContentSpecBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class Item2ViewHolder(val binding: ItemBooleanSpecBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class Item3ViewHolder(val binding: ItemContentIntBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_CONTENT_BOOLEAN -> Item2ViewHolder(
                ItemBooleanSpecBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            TYPE_CONTENT_INT -> Item3ViewHolder(
                ItemContentIntBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )

            else -> {
                Item1ViewHolder(
                    ItemContentSpecBinding.inflate(
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

        when (getItemViewType(position)){
            TYPE_CONTENT_BOOLEAN->{
                val vh = holder as Item2ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.ivCheck.isActivated = data.contentBoolean
            }

            TYPE_CONTENT_INT->{
                val vh = holder as Item3ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.tvContent.text = buildString {
                    append(data.contentInt.toString())
                    append(" ")
                    append(data.unit)
                }
            }

            TYPE_CONTENT ->{
                val vh = holder as Item1ViewHolder
                val context = vh.binding.root.context

                vh.binding.tvTitle.text = data.title?.let { context.getString(it) }
                vh.binding.tvContent.text = data.content
            }
        }
    }

}