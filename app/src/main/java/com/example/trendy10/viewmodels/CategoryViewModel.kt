package com.example.trendy10.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendy10.common.NetworkUtil
import com.example.trendy10.repos.TrendyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val trendyRepo: TrendyRepo,
    private val networkUtil: NetworkUtil
) : ViewModel() {
    val categories: StateFlow<List<String>>
        get() = trendyRepo.categories
    val newsCategories: StateFlow<List<String>>
        get() = trendyRepo.newsCategories
    val quatoCategories: StateFlow<List<String>>
        get() = trendyRepo.quatoCategories

    init {
       fetchCategories()
    }

    fun refreshData() {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            if (networkUtil.hasInternetConnection()) {
                try {
                    trendyRepo.loadAllCategories()
                } catch (e: Exception) {
                    // Handle the exception
                    e.printStackTrace()
                }
            } else {
                println("No internet connection")
            }
        }
    }
}