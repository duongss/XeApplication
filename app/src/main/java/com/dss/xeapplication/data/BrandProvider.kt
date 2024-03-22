package com.dss.xeapplication.data

import com.dss.xeapplication.R
import com.dss.xeapplication.model.Brand

object BrandProvider {
    val ALL = Brand(R.string.all, R.drawable.icons_mitsubishi)

    val TOYOTA = Brand(R.string.toyota, R.drawable.icons_mitsubishi)
    val HONDA = Brand(R.string.honda, R.drawable.icons_mitsubishi)
    val MITSUBISHI = Brand(R.string.mitsubishi, R.drawable.icons_mitsubishi)

    val listBrand = arrayListOf(ALL,MITSUBISHI, TOYOTA, HONDA)
}