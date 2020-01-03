package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        private var mLastId: Int = -1

        fun makeMessage(
            from: User,
            chat: Chat,
            date: Date,
            type: String,
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            mLastId++
            return when (type) {
                "image" -> ImageMessage("$mLastId", from, chat, isIncoming, date, payload as String)
                else -> TextMessage("$mLastId", from, chat, isIncoming, date, payload as String)
            }

        }
    }

}