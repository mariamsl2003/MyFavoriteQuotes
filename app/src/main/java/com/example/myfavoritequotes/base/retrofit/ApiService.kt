package com.example.myfavoritequotes.base.retrofit

import com.example.myfavoritequotes.base.module.QuotesResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("quoteoftheday")
    fun getRandomQuote(): Call<List<QuotesResponse>>

}