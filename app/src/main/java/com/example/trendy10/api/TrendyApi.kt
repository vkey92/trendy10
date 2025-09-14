package com.example.trendy10.api

import com.example.trendy10.models.TweetListItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TrendyApi {

    @GET("v3/b/66a1f567acd3cb34a86b00a5?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String) : Response<List<TweetListItem>>

    @GET("v3/b/66a1f567acd3cb34a86b00a5?meta=false")
    @Headers("X-JSON-Path:tweets..category")
    suspend fun getCategories() : Response<List<String>>

    @GET("v3/b/66a1f567acd3cb34a86b00a5?meta=false")
    @Headers("X-JSON-Path:news..category")
    suspend fun getNewsCategories() : Response<List<String>>

    @GET("v3/b/66a1f567acd3cb34a86b00a5?meta=false")
    @Headers("X-JSON-Path:quotes..category")
    suspend fun getQuatoCategories() : Response<List<String>>
}