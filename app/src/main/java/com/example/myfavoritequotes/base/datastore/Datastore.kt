package com.example.myfavoritequotes.base.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

object Datastore {
    const val PREFERENCE_DATASTORE_APP_NAME = "my_favorite_quotes"

    const val PREF_KEY_FAVORITE_ID = "favorite_id"
    const val PREF_KEY_DARK_MODE = "dark_mode"

    val Context.datastore by preferencesDataStore(name = PREFERENCE_DATASTORE_APP_NAME)

    suspend fun setFavorite(context: Context, value: Int?, key: Preferences.Key<Int>){
        Log.d("DEBUG", "Saving favorite ID: $value")
        context.datastore.edit { preferences ->
            if(value != null)
                preferences[key] = value
            else
                preferences.remove(key)
        }
    }

    suspend fun getFavorite(context: Context, key: Preferences.Key<Int>): Int? {
        val preference = context.datastore.data.first()
        val result = preference[key]
        Log.d("DEBUG", "Retrieving favorite ID: $result")
        return preference[key] ?: null
    }

    suspend fun setDarkMode(context: Context, value: Int, key: Preferences.Key<Int>){
        context.datastore.edit { preferences ->
            preferences[key] = value
        }
    }

    suspend fun getDarkMode(context: Context, key: Preferences.Key<Int>): Int{
        val preferences = context.datastore.data.first()
        return preferences[key] ?: 0 // by default it is false
    }
}