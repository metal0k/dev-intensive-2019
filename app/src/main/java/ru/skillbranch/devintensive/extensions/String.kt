package ru.skillbranch.devintensive.extensions

fun String.truncate(length: Int = 16): String = let {
    if (it.length > length) {
        val lastIdx = if (it.get(length-1) == ' ') length-1 else length
        it.substring(0, lastIdx) + "..."
    } else it

}

fun String.stripHtml() = this
    .replace(Regex("</?.+?>|&.+?;"), "")
    .replace(Regex("\\s{2,}"), " ")
