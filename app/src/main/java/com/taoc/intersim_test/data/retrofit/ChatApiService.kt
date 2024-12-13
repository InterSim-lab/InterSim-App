package com.taoc.intersim_test.data.retrofit

import com.taoc.intersim_test.data.response.ChatResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatApiService {
    @POST("generate/questions")
    suspend fun generateQuestions(
        @Body requestBody: Map<String, Int>
    ): Response<ChatResponse>
}