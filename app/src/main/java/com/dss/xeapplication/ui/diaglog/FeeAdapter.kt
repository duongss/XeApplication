package com.dss.xeapplication.ui.diaglog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.dss.xeapplication.R
import com.dss.xeapplication.base.extension.onAvoidDoubleClick
import com.dss.xeapplication.model.CarFee

class FeeAdapter(
    context: Context,
    private val f: List<CarFee>,
    var onItemClick: (CarFee, Int) -> Unit
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return f.size
    }

    override fun getItem(position: Int): CarFee {
        return f[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: inflater.inflate(R.layout.item_fee, parent, false)
        val data = getItem(position)

        val tvName: TextView = view.findViewById(R.id.tvData)

        tvName.text = data.locationFee

        view.onAvoidDoubleClick {
            onItemClick(data, position)
        }

        return view
    }
}