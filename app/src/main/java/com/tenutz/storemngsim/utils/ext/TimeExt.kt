package com.tenutz.storemngsim.utils.ext

import java.text.SimpleDateFormat
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
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
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

fun Date?.toSimpleDateFormat(): String {
    return this?.run { SimpleDateFormat("yy.MM.dd").format(this) } ?: ""
}

fun Date?.toDateFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd").format(this) } ?: ""
}

fun Date?.toDateTimeFormat(): String {
    return this?.run { SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this) } ?: ""
}

fun Date?.toDateTimeFormatKr(): String {
    return this?.run { SimpleDateFormat("yyyy년 MM월 dd일 a hh:mm:ss").format(this) } ?: ""
}

fun dateFromDateTime(dateText: String): Date {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateText)
}

fun dateFrom(dateText: String): Date {
    return SimpleDateFormat("yyyy-MM-dd").parse(dateText)
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