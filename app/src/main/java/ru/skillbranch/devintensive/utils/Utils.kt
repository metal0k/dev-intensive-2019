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

    fun transliteration(payload: String, divider: String = " "): String {
        val transMap: Map<Char, String> = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya"
        )
        return payload
            .replace(Regex("\\s+"), divider)
            .toCharArray().fold("") { acc, chr ->
                if (transMap.containsKey(chr.toLowerCase()))
                    acc + (transMap.get(chr.toLowerCase()))?.let { if (chr.isUpperCase()) it.capitalize() else it }
                else
                    acc + chr


            }
    }
}



