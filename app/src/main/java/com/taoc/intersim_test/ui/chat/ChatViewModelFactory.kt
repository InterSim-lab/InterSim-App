package com.taoc.intersim_test.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taoc.intersim_test.data.database.ChatHistoryDao

class ChatViewModelFactory(private val chatHistoryDao: ChatHistoryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(chatHistoryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}