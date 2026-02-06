package com.example.myfavoritequotes.base.datastore

import android.content.Context
import com.example.myfavoritequotes.base.module.Quotes

interface IClickListener {

    abstract fun onFavoriteClick(quote: Quotes)
}