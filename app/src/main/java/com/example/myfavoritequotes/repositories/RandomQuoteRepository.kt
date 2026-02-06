package com.example.myfavoritequotes.repositories

import android.content.Context
import android.util.Log
import com.example.myfavoritequotes.base.database.AppDatabase
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.base.module.QuotesResponse
import com.example.myfavoritequotes.base.retrofit.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RandomQuoteRepository {

    fun getRandomQuote(onFinish:(quote: String?, author: String?) -> Unit){
        val call = ApiClient.apiService.getRandomQuote()

        call.enqueue(object : Callback<List<QuotesResponse>>{
            override fun onResponse(
                call: Call<List<QuotesResponse>>,
                response: Response<List<QuotesResponse>>
            ) {
                Log.d("DEBUG", "Response code: ${response.code()}")
                Log.d("DEBUG", "Response body: ${response.body()}")  // Should show the array

                if(response.isSuccessful && response.body() != null){
                    val quoteList = response.body()!!
                    if(quoteList.isNotEmpty()){
                        val firstQuote = quoteList[0]
                        val quote = firstQuote.quote
                        val author = firstQuote.author
                        Log.d("DEBUG", "Parsed: quote='$quote', author='$author'")
                        onFinish(quote, author)
                    }else{
                        Log.d("DEBUG", "list is empty")
                        onFinish(null, null)
                    }
                }
                else{
                    Log.d("DEBUG", "Error: ${response.errorBody()?.string()}")
                    onFinish(null, null)
                }
            }

            override fun onFailure(
                call: Call<List<QuotesResponse>>,
                t: Throwable
            ) {
                Log.d("DEBUG", "Failure: ${t.message}")
                onFinish(null, null)
            }

        })


    }

    fun insert(viewModelScope: CoroutineScope, quote: Quotes, context: Context){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.quoteDao().insert(quote)
        }
    }
}