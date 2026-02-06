package com.example.myfavoritequotes.repositories

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.myfavoritequotes.base.database.AppDatabase
import com.example.myfavoritequotes.base.datastore.Datastore
import com.example.myfavoritequotes.base.module.Quotes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ViewRepository {

    fun getData(viewModelScope: CoroutineScope, context: Context, onFinish:(quotes: List<Quotes>) -> Unit){
       viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            val quotes = db.quoteDao().getAllQuotes()
            onFinish(quotes)
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

    suspend fun isFavorite(context: Context, value: Int): Boolean{
            val db = AppDatabase.getDatabase(context)
            return db.quoteDao().isFavorite(value)

    }

    suspend fun canBeFavorite(context: Context): Boolean{
            val db = AppDatabase.getDatabase(context)
            val quotes = db.quoteDao().getAllQuotes()
            return quotes.any{it.favorite}
    }

    fun unsetFavorite(viewModelScope: CoroutineScope, context: Context, id:Int){
        viewModelScope.launch {
            val db = AppDatabase.getDatabase(context)
            db.quoteDao().unsetFavorite(id)
            Datastore.setFavorite(context, null, intPreferencesKey(Datastore.PREF_KEY_FAVORITE_ID))
        }
    }

}