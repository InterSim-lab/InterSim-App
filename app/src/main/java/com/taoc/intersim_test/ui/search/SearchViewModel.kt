package com.taoc.intersim_test.ui.search

import androidx.lifecycle.ViewModel
import com.taoc.intersim_test.data.response.SearchResponseItem
import com.taoc.intersim_test.data.retrofit.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchViewModel(private val apiService: ApiService) : ViewModel() {
    private val _jobList = MutableStateFlow<List<SearchResponseItem>>(emptyList())
    val jobList: StateFlow<List<SearchResponseItem>> = _jobList

    fun searchJobsByTitle(title: String): Flow<List<SearchResponseItem>> {
        return flow {
            val response = apiService.searchJobs(title)
            if (response.isSuccessful) {
                response.body()?.let { jobs ->
                    _jobList.value = jobs
                    emit(_jobList.value)
                }
            } else {
                _jobList.value = emptyList()
                emit(_jobList.value)
            }
        }.flowOn(Dispatchers.IO)
    }
}