package com.tenutz.storemngsim.utils.ext

import java.text.DecimalFormat
import java.text.NumberFormat
import kotlin.math.pow

val Int.toCurrency: String get() = run {
    val formatter: NumberFormat = DecimalFormat("#,###")
    formatter.format(this)
}

fun randomNumber(n: Int) = ((10.0.pow(n - 1.0).takeIf { it != 1.0 }?.toLong() ?: 0) until 10.0.pow(n.toDouble()).toLong()).random()

val Long.toBusinessNumber: String get() = run {
    val arg1 = "$this".substring(0 until 3)
    val arg2 = "$this".substring(3 until 5)
    val arg3 = "$this".substring(5 until 10)
    "${arg1}-${arg2}-${arg3}"
}

val String?.toBusinessNumber: String get() = takeIf { !isNullOrBlank() }?.run {
    val arg1 = this.substring(0 until 3)
    val arg2 = this.substring(3 until 5)
    val arg3 = this.substring(5 until 10)
    "${arg1}-${arg2}-${arg3}"
} ?: ""

val String?.orEmpty: String get() = this ?: ""