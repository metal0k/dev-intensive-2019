package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.truncate
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import java.util.*

/**
 * Created by Makweb on 24.06.2019.
 */
abstract class BaseMessage(
    val id: String,
    val from: User,
    val chat: Chat,
    val isIncoming: Boolean = true,
    val date: Date = Date(),
    var isReaded: Boolean = false
)

fun BaseMessage?.messageShort(): Pair<String?, String?> = when (this) {
    is TextMessage -> this.text?.trim()?.truncate(64) to "@${this.from.firstName}"
    is ImageMessage -> "${this.from.firstName} отправил фото" to "@${this.from.firstName}"
    else -> null to null
}
