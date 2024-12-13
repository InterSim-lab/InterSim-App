package com.taoc.intersim_test.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChatHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChat(chatHistory: ChatHistory)

    @Query("SELECT * FROM chat_history")
    suspend fun getAllChats(): List<ChatHistory>
}