package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String?>? = fullName?.split(" ")?.map { if (it.isBlank()) null else it }
        return parts?.getOrNull(0) to parts?.getOrNull(1)
    }

}


