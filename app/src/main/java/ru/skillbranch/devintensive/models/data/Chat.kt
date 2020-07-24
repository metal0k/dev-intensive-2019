package ru.skillbranch.devintensive.models.data

import androidx.annotation.VisibleForTesting
import ru.skillbranch.devintensive.extensions.messageShort
import ru.skillbranch.devintensive.models.BaseMessage
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class Chat(
    val id: String,
    val title: String,
    val members: List<User> = listOf(),
    var messages: MutableList<BaseMessage> = mutableListOf(),
    var isArchived: Boolean = false
) {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun unreadableMessageCount(): Int {
        return messages.count { !it.isReaded }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageDate(): Date? {
        return messages.lastOrNull()?.date
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun lastMessageShort(): Pair<String?, String?> = messages.lastOrNull().messageShort()

    private fun isSingle(): Boolean = members.size == 1

    fun toChatItem(): ChatItem {
        return if (isSingle()) {
            val user = members.first()
            ChatItem(
                id,
                user.avatar,
                Utils.toInitials(user.firstName, user.lastName) ?: "??",
                "${user.firstName ?: ""} ${user.lastName ?: ""}",
                lastMessageShort().first,
                unreadableMessageCount(),
                lastMessageDate(),
                user.isOnline
            )
        } else { //GROUP
            ChatItem(
                id,
                null,
                "",
                title,
                lastMessageShort().first,
                messages.size,
                lastMessageDate(),
                false,
                ChatType.GROUP,
                lastMessageShort().second
            )
        }
    }

    companion object ArchiveFactory {
        const val ARCHIVE_ID = "-1"
        fun createArchiveItem(
            lastMessageShort: String,
            messageCount: Int,
            lastMessageDate: Date?,
            lastMessageAuthor: String

        ): ChatItem = ChatItem(
            ARCHIVE_ID,
            null,
            "",
            "",
            lastMessageShort,
            messageCount,
            lastMessageDate,
            false,
            ChatType.ARCHIVE,
            lastMessageAuthor
        )
    }
}

enum class ChatType {
    SINGLE,
    GROUP,
    ARCHIVE
}



