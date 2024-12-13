package com.taoc.intersim_test.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taoc.intersim_test.data.response.CategoryResponseItem
import com.taoc.intersim_test.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {
    fun fetchJobsByCategory(category: String, limit: Int, callback: (List<CategoryResponseItem>?) -> Unit) {
        viewModelScope.launch {
            val response = ApiConfig.getApiService().getJobsByCategory(category, limit)
            if (response.isSuccessful) {
                callback(response.body())
            } else {
                callback(null)
            }
        }
    }
}