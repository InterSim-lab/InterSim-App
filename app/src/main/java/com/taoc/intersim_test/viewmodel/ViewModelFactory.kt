package com.taoc.intersim_test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.taoc.intersim_test.data.retrofit.ApiService
import com.taoc.intersim_test.ui.detailjobs.DetailViewModel
import com.taoc.intersim_test.ui.search.SearchViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiService: ApiService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> {
                SearchViewModel(apiService) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(apiService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}