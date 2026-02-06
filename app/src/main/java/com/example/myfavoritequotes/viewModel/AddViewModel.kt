package com.example.myfavoritequotes.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.repositories.AddRepository

class AddViewModel: ViewModel() {

    private val repository: AddRepository = AddRepository()
    private val liveFavorite = MutableLiveData<Boolean>()

    fun liveFavorite() = liveFavorite

    fun toggleButton(quote: String, author: String): Boolean{
        return quote.isNotEmpty() && author.isNotEmpty()
    }

    fun canBeFavorite(context: Context){
        repository.canBeFavorite(viewModelScope, context){ canFavorite ->
            liveFavorite.value = canFavorite
        }
    }

    fun setFavorite(context: Context, id: Int){
        repository.settingFavorite(context, id, viewModelScope)
    }

     fun insert(quote: Quotes, context: Context, favorite: Boolean){
        repository.insert(viewModelScope, quote, context)
         if(favorite){
             setFavorite(context, quote.id)
         }
    }
}