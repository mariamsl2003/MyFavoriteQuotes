package com.example.myfavoritequotes.base.retrofit

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

object RetrofitClient {
    const val BASE_URL = "https://api.api-ninjas.com/v2/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient().build())
            .build()
    }

    private fun okHttpClient() = okhttp3.OkHttpClient()
        .newBuilder().addInterceptor { chain ->
            val request: okhttp3.Request = chain.request()
                .newBuilder()
                .header("X-Api-Key", "IgIdhc6U6uDe4kcc2Z8tHVHrNB4yVCJf8RP1BzCY") // acc to api
                .build()
            Log.d("DEBUG", "okHttpClient: the request work fine- I guess")
            chain.proceed(request)
        }
}