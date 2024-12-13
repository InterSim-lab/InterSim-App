package com.taoc.intersim_test.data.retrofit

import com.taoc.intersim_test.data.response.CategoryResponseItem
import com.taoc.intersim_test.data.response.ChatResponse
import com.taoc.intersim_test.data.response.DetailResponseItem
import com.taoc.intersim_test.data.response.JobsResponseItem
import com.taoc.intersim_test.data.response.SearchResponse
import com.taoc.intersim_test.data.response.SearchResponseItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/jobs/random")
    suspend fun getJobs(
        @Query("limit") limit: Int
    ): Response<List<JobsResponseItem>>

    @GET("api/jobs/{id}")
    suspend fun getJobDetails(
        @Path("id") Id: Int
    ): Response<DetailResponseItem>

    @GET("api/jobs")
    suspend fun searchJobs(
        @Query("title") title: String
    ): Response<List<SearchResponseItem>>

    @GET("api/jobs/category/{category}")
    suspend fun getJobsByCategory(
        @Path("category") category: String,
        @Query("limit") limit: Int
    ): Response<List<CategoryResponseItem>>
}