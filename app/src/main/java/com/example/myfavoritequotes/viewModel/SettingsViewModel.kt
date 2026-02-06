package com.example.myfavoritequotes.viewModel

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritequotes.base.datastore.Datastore
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.repositories.SettingsRepository
import com.google.gson.Gson

class SettingsViewModel: ViewModel() {

    val repository: SettingsRepository = SettingsRepository()
    private val liveFavorite = MutableLiveData<Quotes?>()

    fun liveFavorite() = liveFavorite


    suspend fun getFavorite(context: Context){
        repository.getFavoriteQuote(context){quote ->
            liveFavorite.postValue(quote)
        }
    }

    fun clearAllQuotes(context: Context){
        repository.clearQuotes(viewModelScope, context)
    }

    suspend fun getAllQuotes(context: Context): List<Quotes>{
        return repository.getAllQuotes(context)
    }

    fun setDarkMode(context: Context, enabled: Int){
        repository.setDarkMode(viewModelScope, context, enabled)
    }
    suspend fun isDarkModeEnabled(context: Context): Int {
        return repository.isDarkModeEnabled(context)
    }

}