package com.taoc.intersim_test.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taoc.intersim_test.data.response.JobsResponseItem
import com.taoc.intersim_test.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _jobList = MutableLiveData<List<JobsResponseItem?>>()
    val jobList: LiveData<List<JobsResponseItem?>> get() = _jobList

    fun fetchJobs(limit: Int) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().getJobs(limit)
            if (response.isSuccessful) {
                _jobList.value = response.body()
            }
        }
    }
}