package ru.skillbranch.devintensive.utils

import java.util.*

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String?>? = fullName?.split(" ")?.map { if (it.isBlank()) null else it }
        return parts?.getOrNull(0) to parts?.getOrNull(1)
    }

    fun toInitials(firstName: String?, lastName: String?): String? =
        listOf(firstName, lastName)
            .filter { !it.isNullOrBlank() }
            .let{
                if(!it.isEmpty())
                    it.joinToString (separator = "")
                        { it?.get(0)?.toUpperCase().toString() }
                else
                    null
            }


}


