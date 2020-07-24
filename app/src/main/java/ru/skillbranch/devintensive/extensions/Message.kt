package ru.skillbranch.devintensive.extensions

import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.models.ImageMessage
import ru.skillbranch.devintensive.models.TextMessage

fun BaseMessage?.messageShort(): Pair<String?, String?> = when (this) {
    is TextMessage -> this.text?.trim()?.truncate(64) to this.from.firstName
    is ImageMessage -> "${this.from.firstName} отправил фото" to this.from.firstName
    else -> null to null
}