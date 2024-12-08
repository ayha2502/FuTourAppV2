package com.syzlnnuro.futourappv2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface ApiService {
    @GET("places")
    fun getPlaces(@Query("active") active: Int): Call<PlaceResponse>

    @GET("places")
    fun searchPlaces(
        @Query("q") query: String,
        @Query("active") active: Int
    ): Call<PlaceResponse>

}