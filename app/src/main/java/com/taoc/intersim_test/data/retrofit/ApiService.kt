package com.taoc.intersim_test.data.retrofit

import com.taoc.intersim_test.data.response.JobsResponseItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    @GET("api/jobs/random")
    suspend fun getJobs(
        @Query("limit") limit: Int
    ): Response<List<JobsResponseItem>>

    @GET("api/jobs/{id}")
    fun getJobDetails(
        @Path("id") jobId: String
    ): Call<JobsResponseItem>
}