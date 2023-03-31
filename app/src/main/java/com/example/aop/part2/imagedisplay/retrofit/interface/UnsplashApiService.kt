package com.example.aop.part2.imagedisplay.retrofit.`interface`

import com.example.aop.part2.imagedisplay.BuildConfig
import com.example.aop.part2.imagedisplay.models.PhotoResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {


    @GET(
        "photos/random/?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}"
    )
    fun getPhotos(@Query("count") count: Int?): Call<List<PhotoResponseItem>>

    @GET(
        "photos/random/?" +
                "client_id=${BuildConfig.UNSPLASH_ACCESS_KEY}"+
                "&count=10"
    )
    fun searchPhotos(@Query("query") query: String?): Call<List<PhotoResponseItem>>
}