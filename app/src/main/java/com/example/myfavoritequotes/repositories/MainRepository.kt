package com.example.myfavoritequotes.repositories

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.myfavoritequotes.base.datastore.Datastore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainRepository {

    suspend fun isDarkModeEnabled(context: Context): Int{
        return Datastore.getDarkMode(context, intPreferencesKey(Datastore.PREF_KEY_DARK_MODE))
    }
}