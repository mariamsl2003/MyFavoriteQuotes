package com.example.myfavoritequotes.base.module

import com.google.gson.annotations.SerializedName

data class QuotesResponse (
    @SerializedName("quote") val quote: String?,
    @SerializedName("author") val author: String?,
    @SerializedName("work") val work: String?, //won't be used just for the api response
    @SerializedName("categories") val categories: List<String>? //won't be used just for the api response
)