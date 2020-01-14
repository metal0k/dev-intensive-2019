package ru.skillbranch.devintensive.extensions

import android.util.Log

const val LOG_TAG = "M_"


fun Any.log(message: String, level:Int = Log.DEBUG) {
    val tag = "$LOG_TAG${this::class.simpleName}"
    Log.println(level, tag, message)
}

