package com.example.myfavoritequotes.base

import com.example.myfavoritequotes.base.module.Quotes

interface BClickListener {
    abstract fun onExportClick(quotes: List<Quotes>)
}