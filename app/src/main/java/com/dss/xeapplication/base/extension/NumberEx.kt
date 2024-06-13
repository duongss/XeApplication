package com.dss.xeapplication.base.extension

import java.text.NumberFormat
import java.util.Locale

fun Long.toCurrencyFormat(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.GERMANY)
    return numberFormat.format(this)
}