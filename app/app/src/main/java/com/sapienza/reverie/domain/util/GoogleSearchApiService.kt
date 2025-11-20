package com.sapienza.reverie.domain.util

// app/src/main/java/com/sapienza/reverie/domain/repository/GoogleSearchRepository.kt

import com.google.gson.annotations.SerializedName
import com.sapienza.reverie.properties.ApiProperties
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Data classes to parse the JSON response from Google
data class GoogleSearchResponse(@SerializedName("items") val items: List<ImageResult>)
data class ImageResult(
    @SerializedName("link") val link: String?,
    @SerializedName("image") val image: ImageInfo?
)

data class ImageInfo(
    @SerializedName("thumbnailLink") val thumbnail: String?
)

// Retrofit interface for the Google Search API
interface GoogleSearchApiService {
    @GET("customsearch/v1")
    suspend fun searchImages(
        @Query("key") apiKey: String = ApiProperties.GOOGLE_API_KEY,
        @Query("cx") cx: String = ApiProperties.GOOGLE_CX,
        @Query("q") query: String,
        @Query("searchType") searchType: String = "image",
        @Query("num") num: Int = 10 // Number of results
    ): GoogleSearchResponse
}

class GoogleSearchRepository {

    private val apiService: GoogleSearchApiService

    init {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        apiService = retrofit.create(GoogleSearchApiService::class.java)
    }

    suspend fun searchImages(query: String): List<ImageResult> {
        val response = apiService.searchImages(query = query)
        return response.items ?: emptyList()
    }
}