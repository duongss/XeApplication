package com.dss.xeapplication.data

import com.dss.xeapplication.R
import com.dss.xeapplication.model.Brand

object BrandProvider {
    val ALL = Brand("ALL", R.drawable.icons_mitsubishi)


    val MITSUBISHI = Brand("Mitsubishi", R.drawable.icons_mitsubishi)

    val listBrand = arrayListOf(ALL,MITSUBISHI)
}