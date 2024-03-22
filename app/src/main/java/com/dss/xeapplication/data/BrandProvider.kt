package com.dss.xeapplication.data

import com.dss.xeapplication.R
import com.dss.xeapplication.model.Brand

object BrandProvider {
    val ALL = Brand(R.string.all, "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/mitsubishi.png?alt=media&token=8d9ecbd4-93c0-45fe-814f-7a512ff7d281")

    val TOYOTA = Brand(R.string.toyota, "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/mitsubishi.png?alt=media&token=8d9ecbd4-93c0-45fe-814f-7a512ff7d281")
    val HONDA = Brand(R.string.honda, "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/mitsubishi.png?alt=media&token=8d9ecbd4-93c0-45fe-814f-7a512ff7d281")
    val MITSUBISHI = Brand(R.string.mitsubishi, "https://firebasestorage.googleapis.com/v0/b/xeapp-2a308.appspot.com/o/mitsubishi.png?alt=media&token=8d9ecbd4-93c0-45fe-814f-7a512ff7d281")

    val listBrand = arrayListOf(ALL,MITSUBISHI, TOYOTA, HONDA)
}