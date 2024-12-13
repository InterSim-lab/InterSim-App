package com.taoc.intersim_test.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat_history")
data class ChatHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val jobId: String,
    val message: String,
    val timestamp: Long,
    val title: String,
    val company: String,
    val imageUrl: String
)
