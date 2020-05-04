package com.example.local_syogi.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

/**
 * サーバー情報
 */

object Provider {
    private lateinit var apiService: ApiService

    fun testApi(): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://socket-sample-th.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        apiService = retrofit.create(ApiService::class.java)
        return apiService
    }
}