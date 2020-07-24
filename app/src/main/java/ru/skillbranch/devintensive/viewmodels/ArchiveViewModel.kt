package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.addIf
import ru.skillbranch.devintensive.extensions.messageShort
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class ArchiveViewModel: ViewModel() {
    private val chatRepository = ChatRepository;

    private val chats = Transformations.map(chatRepository.loadChats()) { chats ->
        chats
            .filter { it.isArchived }
            .map{it.toChatItem()}
            .sortedBy { it.id.toInt() }
    }
    fun getChatData(): LiveData<List<ChatItem>> {
        return chats
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

}