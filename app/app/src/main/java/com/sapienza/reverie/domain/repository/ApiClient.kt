package com.sapienza.reverie.domain.repository

import com.sapienza.reverie.properties.ApiProperties
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



object ApiClient {



    // Create your service interface instance

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiProperties.API_BASE_PATH) // ✅ Replace with your Spring server URL
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service: CharmRepository = retrofit.create(CharmRepository::class.java)
}