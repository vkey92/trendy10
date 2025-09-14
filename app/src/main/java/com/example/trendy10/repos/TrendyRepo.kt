package com.example.trendy10.repos

import com.example.trendy10.api.TrendyApi
import com.example.trendy10.models.TweetListItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TrendyRepo @Inject constructor(private val trendyApi: TrendyApi) {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories : StateFlow<List<String>>
        get() = _categories

    private val _newsCategories = MutableStateFlow<List<String>>(emptyList())
    val newsCategories : StateFlow<List<String>>
        get() = _newsCategories

    private val _quatoCategories = MutableStateFlow<List<String>>(emptyList())
    val quatoCategories : StateFlow<List<String>>
        get() = _quatoCategories

    private val _tweets = MutableStateFlow<List<TweetListItem>>(emptyList())
    val tweets : StateFlow<List<TweetListItem>>
        get() = _tweets

    suspend fun getCategories(){
        val response = trendyApi.getCategories()
        if(response.body() != null && response.isSuccessful){
           _categories.emit(response.body()!!)
           getNewsCategories()
        }
    }

    suspend fun getNewsCategories(){
        val response = trendyApi.getNewsCategories()
        if(response.body() != null && response.isSuccessful){
            _newsCategories.emit(response.body()!!)
            getQuatoCategories()
        }
    }

    suspend fun getQuatoCategories(){
        val response = trendyApi.getQuatoCategories()
        if(response.body() != null && response.isSuccessful){
            _quatoCategories.emit(response.body()!!)
        }
    }

    suspend fun getTweets(category: String,type : String){
        val response = trendyApi.getTweets("$type[?(@.category==\"$category\")]")
        if(response.body() != null && response.isSuccessful){
            _tweets.emit(response.body()!!)
        }
    }

}