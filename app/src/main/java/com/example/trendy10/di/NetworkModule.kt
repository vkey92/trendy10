package com.example.trendy10.di

import android.content.Context
import com.example.trendy10.api.TrendyApi
import com.example.trendy10.common.NetworkUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl("https://api.jsonbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesTrendyApi(retrofit: Retrofit):TrendyApi{
        return retrofit.create(TrendyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideNetworkUtil(context: Context): NetworkUtil {
        return NetworkUtil(context)
    }


}