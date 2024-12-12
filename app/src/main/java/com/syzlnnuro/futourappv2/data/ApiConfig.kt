package com.syzlnnuro.futourappv2.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ApiConfig {
    private const val BASE_URL = "https://backendimg-657666674735.asia-southeast2.run.app/"

    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ApiService::class.java)
    }
}