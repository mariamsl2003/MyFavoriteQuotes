package com.example.myfavoritequotes.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritequotes.base.datastore.IClickListener
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.repositories.ViewRepository

class ViewViewModel: ViewModel() {


    private val repository: ViewRepository = ViewRepository()
    private val liveQuotes = MutableLiveData<List<Quotes>>()

    fun liveQuotes() = liveQuotes

    fun getData(context: Context){
       return repository.getData(viewModelScope, context){quotes ->
            liveQuotes.value = quotes
        }
    }

    fun setFavorite(context: Context, favoriteId: Int){
        repository.settingFavorite(context, favoriteId, viewModelScope)
    }

    suspend fun isFavorite(context: Context, id: Int): Boolean{
        return repository.isFavorite(context, id)
    }

    suspend fun canBeFavorite(context: Context): Boolean{
        return repository.canBeFavorite(context)
    }

    fun unsetFavorite(context: Context, id: Int){
        repository.unsetFavorite(viewModelScope, context, id)
    }
}