package com.taoc.intersim_test.ui.detailjobs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taoc.intersim_test.data.response.DetailResponseItem
import com.taoc.intersim_test.data.retrofit.ApiService
import kotlinx.coroutines.launch

class DetailViewModel(private val apiService: ApiService) : ViewModel() {
    private val _jobDetail = MutableLiveData<DetailResponseItem?>()
    val jobDetail: LiveData<DetailResponseItem?> get() = _jobDetail

    fun fetchJobDetails(id: Int) {
        viewModelScope.launch {
            val response = apiService.getJobDetails(id)
            if (response.isSuccessful) {
                _jobDetail.value = response.body()
            }
        }
    }
}