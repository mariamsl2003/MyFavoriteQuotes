package com.example.myfavoritequotes.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.room.Database
import com.example.myfavoritequotes.base.database.AppDatabase
import com.example.myfavoritequotes.base.datastore.Datastore
import com.example.myfavoritequotes.base.module.Quotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SettingsRepository {

    suspend fun getFavoriteQuote(context: Context, onFinish:(quote: Quotes?) -> Unit){
            val id = favoriteQuoteId(context)
        Log.d("DEBUG", "Favorite ID from DataStore in getFavoriteQuote: $id")
        if (id != null) {
            try {
                val db = AppDatabase.getDatabase(context)
                val quote = db.quoteDao().getFavoriteQuote(id)
                onFinish(quote)  // Success: Pass the quote
            } catch (e: Exception) {
                // Handle case where no quote matches the ID (e.g., quote deleted)
                Log.e("DEBUG", "Favorite quote not found for ID $id: ${e.message}", e)
                onFinish(null)  // No favorite
            }
        } else {
            onFinish(null)  // No favorite ID stored
        }
    }

    suspend fun favoriteQuoteId(context: Context): Int?{
        return Datastore.getFavorite(context, intPreferencesKey(Datastore.PREF_KEY_FAVORITE_ID))
    }

    fun clearQuotes(viewModelScope: CoroutineScope, context: Context){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.quoteDao().deleteAll()
            Datastore.setFavorite(context, 0, intPreferencesKey(Datastore.PREF_KEY_FAVORITE_ID))
        }
    }

    suspend fun getAllQuotes(context: Context): List<Quotes>{
        var quotes = listOf<Quotes>()
            val db = AppDatabase.getDatabase(context)
            quotes = db.quoteDao().getAllQuotes()

        return quotes
    }

    //save the current mode in shared preferences
     fun setDarkMode(viewModelScope: CoroutineScope, context: Context, enabled: Int){
         viewModelScope.launch {
             Datastore.setDarkMode(context, enabled, intPreferencesKey(Datastore.PREF_KEY_DARK_MODE))
         }
    }

    suspend fun isDarkModeEnabled(context: Context): Int {
        return Datastore.getDarkMode(context, intPreferencesKey(Datastore.PREF_KEY_DARK_MODE))
    }
}