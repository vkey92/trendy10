package com.example.trendy10.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendy10.models.TweetListItem
import com.example.trendy10.repos.TrendyRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val trendyRepo: TrendyRepo,private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val tweets : StateFlow<List<TweetListItem>>
        get() = trendyRepo.tweets
    var category : String = ""
    var type : String = ""

    init {
        viewModelScope.launch {
             category = savedStateHandle.get<String>("category") ?: ""
             type = savedStateHandle.get<String>("type") ?: ""
            trendyRepo.getTweets(category,type)
        }
    }
}