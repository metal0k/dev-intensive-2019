package ru.skillbranch.devintensive.extensions

import android.text.format.DateUtils
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*


fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy") =
    SimpleDateFormat(pattern, Locale("ru")).format(this)

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String = when (abs(value) % 10) {
            1 -> "$value секунду"
            in 2..4 -> "$value секунды"
            else -> "$value секунд"
        }

    },
    MINUTE {
        override fun plural(value: Int): String = when (abs(value) % 10) {
            1 -> "$value минуту"
            in 2..4 -> "$value минуты"
            else -> "$value минут"
        }
    },
    HOUR {
        override fun plural(value: Int): String = when (abs(value) % 10) {
            1 -> "$value час"
            in 2..4 -> "$value часа"
            else -> "$value часов"
        }
    },
    DAY {
        override fun plural(value: Int): String = when (abs(value) % 10) {
            1 -> "$value день"
            in 2..4 -> "$value дня"
            else -> "$value дней"
        }
    };
    abstract fun plural(value: Int): String
}

fun Date.add(value: Int, units: TimeUnits): Date = this.apply {
    time += when (units) {
        TimeUnits.SECOND -> value * DateUtils.SECOND_IN_MILLIS
        TimeUnits.MINUTE -> value * DateUtils.MINUTE_IN_MILLIS
        TimeUnits.HOUR -> value * DateUtils.HOUR_IN_MILLIS
        TimeUnits.DAY -> value * DateUtils.DAY_IN_MILLIS
    }
}