package com.taoc.intersim_test.data.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/jobs/random")
    fun getJobs(@Query("limit") limit: Int): Call<List<Job>>
}