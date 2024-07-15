package com.tenutz.storemngsim.utils.ext

import java.text.DecimalFormat
import java.text.NumberFormat

val Int.toCurrency: String get() = run {
    val formatter: NumberFormat = DecimalFormat("#,###")
    formatter.format(this)
}