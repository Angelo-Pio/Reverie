package com.sapienza.reverie.domain.repository

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
        .baseUrl("http://10.0.2.2:6001/reverie/api/") // ✅ Replace with your Spring server URL
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val service: CharmRepository = retrofit.create(CharmRepository::class.java)
}