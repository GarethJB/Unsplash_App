package com.example.aop.part2.imagedisplay.retrofit.repository

import com.example.aop.part2.imagedisplay.retrofit.`interface`.UnsplashApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApi {
    private const val BASE_URL = "https://api.unsplash.com/"

    private val okHttpClient: OkHttpClient by lazy {               // 서버와 코어 사이에서 데이터를 가로채는 기능
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()                                         // Builder 패턴을 사용해서 레트로핏 객체 생성
            .addConverterFactory(GsonConverterFactory.create())    // DTO 변환에 사용하는 JSON 컨버터 지정
            .client(okHttpClient)                                  // client 속성에 okHttpClient 의 인스턴스를 넘겨줌으로써 로그캣에서 패킹 내용 모니터링
            .baseUrl(BASE_URL)                                     // BASE_URL 전달
            .build()                                               // 객체 생성
    }

    val unsplashApiService: UnsplashApiService by lazy {
        retrofit.create(UnsplashApiService::class.java)            // retrofit 인스턴스에 create 명령으로 Service 인스턴스를 생성 ← 실제로 사용
    }

}