package com.example.trendy10.repos

import com.example.trendy10.api.TrendyApi
import com.example.trendy10.models.TweetListItem
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
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

    suspend fun loadAllCategories() = coroutineScope {
        val categoriesDeferred = async { trendyApi.getCategories() }
        val newsDeferred = async { trendyApi.getNewsCategories() }
        val quotesDeferred = async { trendyApi.getQuatoCategories() }

        val categoriesResponse = categoriesDeferred.await()
        val newsResponse = newsDeferred.await()
        val quotesResponse = quotesDeferred.await()

        if (categoriesResponse.isSuccessful) {
            _categories.emit(categoriesResponse.body()!!)
        }
        if (newsResponse.isSuccessful) {
            _newsCategories.emit(newsResponse.body().orEmpty())
        }
        if (quotesResponse.isSuccessful) {
            _quatoCategories.emit(quotesResponse.body().orEmpty())
        }
    }



    suspend fun getTweets(category: String,type : String){
        val response = trendyApi.getTweets("$type[?(@.category==\"$category\")]")
        if(response.body() != null && response.isSuccessful){
            _tweets.emit(response.body()!!)
        }
    }

}