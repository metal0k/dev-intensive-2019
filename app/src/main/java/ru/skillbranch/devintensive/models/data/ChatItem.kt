package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.shortFormat
import java.util.*


data class ChatItem (
    val id: String,
    val avatar: String?,
    val initials: String,
    val title: String,
    val shortDescription: String?,
    val messageCount: Int = 0,
    val lastMessageDate: Date?,
    val isOnline: Boolean = false,
    val chatType : ChatType = ChatType.SINGLE,
    var author :String? = null
) {
    var lastMessageDateShort: String? = null
      get() = lastMessageDate?.shortFormat()
}