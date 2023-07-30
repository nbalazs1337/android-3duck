package com.balazs.project.networking

import RealEstateApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://us-real-estate.p.rapidapi.com/"

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "86349e7fd4msh3d0d022bf78fe1ep10a38ajsnd94d49bf804c")
                .addHeader("X-RapidAPI-Host", "us-real-estate.p.rapidapi.com")
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val realEstateApiService: RealEstateApiService = retrofit.create(RealEstateApiService::class.java)
}

