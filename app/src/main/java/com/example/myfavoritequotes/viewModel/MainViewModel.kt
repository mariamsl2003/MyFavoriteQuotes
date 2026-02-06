package com.example.myfavoritequotes.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfavoritequotes.repositories.MainRepository

class MainViewModel: ViewModel() {

    var repository: MainRepository = MainRepository()

    suspend fun isDarkModeEnabled(context: Context): Int{
        return repository.isDarkModeEnabled(context)
    }
}