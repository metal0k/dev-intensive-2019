package ru.skillbranch.devintensive.extensions

import android.util.Log

const val LOG_TAG = "M_"


fun Any.log_d(message: String) {
    val tag = "$LOG_TAG${this::class.simpleName}"
    Log.d(tag, message)
}

