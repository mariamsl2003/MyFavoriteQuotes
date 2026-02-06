package com.example.myfavoritequotes.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.myfavoritequotes.base.database.AppDatabase
import com.example.myfavoritequotes.base.datastore.Datastore
import com.example.myfavoritequotes.base.module.Quotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AddRepository {

    fun canBeFavorite(viewModelScope: CoroutineScope, context: Context, onFinish:(canFavorite: Boolean) -> Unit){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val quotes = db.quoteDao().getAllQuotes()
            var isFavorite = false
            for(quote in quotes){
                if(quote.favorite){
                    isFavorite = true //there is a favorite
                }
            }
            if(isFavorite){
                onFinish(false) //this one can't be the favorite
            }else{
                onFinish(true) //this one can be the favorite
            }
        }
    }

    fun insert(viewModelScope: CoroutineScope, quote: Quotes, context: Context){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.quoteDao().insert(quote)
        }
    }

    fun settingFavorite(context: Context, value: Int, viewModelScope: CoroutineScope){
        viewModelScope.launch {
            Log.d("DEBUG", "Saving favorite ID in Repository: $value")
            val db = AppDatabase.getDatabase(context)
            db.quoteDao().updateNewFavorite(value)
            if(value <=0){
                Log.d("DEBUG", "Invalid Favorite Id, id= $value")
            }else
                Datastore.setFavorite(context, value, intPreferencesKey(Datastore.PREF_KEY_FAVORITE_ID))
        }
    }
}