package com.taoc.intersim_test.ui.chat


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taoc.intersim_test.data.database.ChatHistoryDao
import com.taoc.intersim_test.data.response.ChatResponse
import com.taoc.intersim_test.data.retrofit.ChatApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ChatViewModel(private val chatHistoryDao: ChatHistoryDao) : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://intersim-qs-rebuild-8724644534.us-central1.run.app/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val chatApiService = retrofit.create(ChatApiService::class.java)

    fun generateQuestions(
        jobId: Int,
        onSuccess: (ChatResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestBody = mapOf("id" to jobId)
                val response = chatApiService.generateQuestions(requestBody)

                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            onSuccess(it)
                        } ?: onError("Tidak ada pertanyaan yang dihasilkan")
                    } else {
                        onError("Gagal menghasilkan pertanyaan: ${response.message()}")
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    onError("Terjadi kesalahan: ${e.message}")
                }
            }
        }
    }
}