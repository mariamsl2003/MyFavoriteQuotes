package com.example.myfavoritequotes.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.repositories.RandomQuoteRepository

class RandomQuoteViewModel: ViewModel() {

    var repository: RandomQuoteRepository = RandomQuoteRepository()

    private val liveRandomQuote = MutableLiveData<Quotes?>()

    fun liveRandomQuote() = liveRandomQuote

    fun getRandomQuote(){
        repository.getRandomQuote { quote, author ->
            Log.d("DEBUG", "in getRandomQuote viewModel: quote is $quote and author is $author")
            val quotes = Quotes()
            quotes.quote = quote?: "Api returned, but it is null"
            quotes.author = author?: "Api returned, but it is null"
            liveRandomQuote.value = quotes
        }
    }

    fun insert(quote: Quotes, context: Context){
        repository.insert(viewModelScope, quote, context)
    }
}