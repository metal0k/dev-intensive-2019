package ru.skillbranch.devintensive.extensions

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

fun Date.humanizeDiff(): String {
    return this.toString()
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") =
    SimpleDateFormat(pattern, Locale("ru")).format(this)

enum class TimeUnits {
    SECOND, MINUTE, HOUR, DAY
}

fun Date.add(value: Int, units: TimeUnits): Date = this.apply {
    time += when (units) {
        TimeUnits.SECOND -> value * DateUtils.SECOND_IN_MILLIS
        TimeUnits.MINUTE -> value * DateUtils.MINUTE_IN_MILLIS
        TimeUnits.HOUR -> value * DateUtils.HOUR_IN_MILLIS
        TimeUnits.DAY -> value * DateUtils.DAY_IN_MILLIS
    }
}