package com.dss.xeapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dss.xeapplication.R
import com.dss.xeapplication.base.BaseRecyclerViewAdapter
import com.dss.xeapplication.base.extension.invisible
import com.dss.xeapplication.base.extension.visible
import com.dss.xeapplication.databinding.ItemBrandBinding
import com.dss.xeapplication.model.Brand
import com.dss.xeapplication.model.SpecificationsCar

class AdapterSpec : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataList: MutableList<SpecificationsCar> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}