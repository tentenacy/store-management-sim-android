package com.tenutz.storemngsim.utils.ext

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.*

fun yesterday(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return cal.time
}

fun today(): Date {
    val cal = Calendar.getInstance()
    cal.time = Date()
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

fun tomorrow(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

fun sevenDaysAgo(): Date {
    val cal = Calendar.getInstance()
    cal.time = today()
    cal.add(Calendar.DAY_OF_MONTH, -7)
    return cal.time
}

@SuppressLint("SimpleDateFormat")
fun Date?.toDateFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd").format(this) } ?: ""
}

@SuppressLint("SimpleDateFormat")
fun Date?.toDateFormatKr(): String {
    return this?.run { SimpleDateFormat("yyyy.MM.dd(E)").format(this) } ?: ""
}

@SuppressLint("SimpleDateFormat")
fun Date?.toDateFormat(pattern: String): String {
    return this?.run { SimpleDateFormat(pattern).format(this) } ?: ""
}

@SuppressLint("SimpleDateFormat")
fun Date?.toDateTimeFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this) } ?: ""
}

fun LocalDate?.toDateFormat(pattern: String = "yyyy-MM-dd"): String {
    return this?.run { DateTimeFormatter.ofPattern(pattern).format(this) } ?: ""
}

fun LocalDate?.toDate(): Date? {
    return this?.run { Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant()) }
}

@SuppressLint("SimpleDateFormat")
fun dateFrom(dateText: String, pattern: String = "yyyy-MM-dd"): Date? {
    return try {
        SimpleDateFormat(pattern).parse(dateText)
    } catch (e: ParseException) {
        null
    }
}

fun localDateFrom(dateText: String?, pattern: String = "yyyy-MM-dd"): LocalDate? {
    return try {
        dateText.takeIf { !it.isNullOrBlank() }?.let { LocalDate.parse(it, DateTimeFormatter.ofPattern(pattern)) }
    } catch (e: DateTimeParseException) {
        null
    }
}

@SuppressLint("SimpleDateFormat")
fun timeFrom(timeText: String? = "HHmm"): Date? {
    return try {
        SimpleDateFormat(timeText).parse(timeText)
    } catch (e: ParseException) {
        null
    }
}

fun Date.start(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

fun Date.end(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 23)
    cal.set(Calendar.MINUTE, 59)
    cal.set(Calendar.SECOND, 59)
    cal.set(Calendar.MILLISECOND, 999)
    return cal.time
}

fun Date.noon(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.set(Calendar.HOUR_OF_DAY, 12)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.time
}

fun Date.yesterday(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return cal.time
}

fun Date.tomorrow(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

fun Date.year(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.YEAR)
}

fun Date.month(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.MONTH) + 1
}

fun Date.day(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.DAY_OF_MONTH)
}

fun Date.hour(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.HOUR_OF_DAY)
}

fun Date.minute(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.MINUTE) + 1
}

fun Date.second(): Int {
    val cal = Calendar.getInstance()
    cal.time = this
    return cal.get(Calendar.SECOND)
}

fun Date.minusDay(day: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, -day)
    return cal.time
}

fun Date.minusMonth(month: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, -month)
    return cal.time
}

fun Date.minusYear(year: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.YEAR, -year)
    return cal.time
}

fun calendarFrom(dateText: String): Calendar {
    return dateText.takeIf { it != "-" }?.let {
        val token = dateText.split("-")
        Calendar.getInstance().apply {
            set(token[0].substring(0,4).toInt(), token[1].substring(0, 2).toInt() - 1, token[2].substring(0, 2).toInt())
        }
    } ?: run {
        Calendar.getInstance().apply {
            time = Date()
        }
    }
}