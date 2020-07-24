package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.addIf
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.TextMessage
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.messageShort
import ru.skillbranch.devintensive.repositories.ChatRepository
import ru.skillbranch.devintensive.utils.DataGenerator

class MainViewModel: ViewModel() {
    private val chatRepository = ChatRepository;
    private val query = mutableLiveData("")

    private val chats = Transformations.map(chatRepository.loadChats()) { chats ->
        val archiveChats = chats.filter {
            it.isArchived
        }
        val archiveMessages = archiveChats
            .flatMap { it.messages }
            .sortedBy { it.date }

        val lastMessageArchive = archiveMessages.lastOrNull(){!it.isReaded}
        val (lastMessageText, lastMessageAuthor) = lastMessageArchive.messageShort()
        chats
            .filter { !it.isArchived }
            .map{it.toChatItem()}
            .sortedBy { it.id.toInt() }
            .toMutableList()
            .addIf(
                Chat.createArchiveItem(
                lastMessageText ?: "Нет сообщений",
                archiveMessages.count(){!it.isReaded},
                lastMessageArchive?.date,
                lastMessageAuthor ?: ""
            ),0
            ) {archiveChats.isNotEmpty()}
    }


    fun getChatData(): LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()
        val filterFunc = {
            val queryString = query.value
            val users = chats.value
            result.value = if (queryString.isNullOrEmpty()) users
            else users?.filter { it.title.contains(queryString, true) }
        }

        result.addSource(chats){filterFunc()}
        result.addSource(query){filterFunc()}

        return result
    }


    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(searchText: String?) {
        query.value = searchText
    }

}