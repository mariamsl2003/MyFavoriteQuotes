package com.example.myfavoritequotes.recyclerView.viewHolder

import android.content.Context
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritequotes.R
import com.example.myfavoritequotes.base.datastore.IClickListener
import com.example.myfavoritequotes.databinding.ViewHolderQuotesBinding
import com.example.myfavoritequotes.base.module.Quotes

class QuotesViewHolder(val binding: ViewHolderQuotesBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(quote: Quotes){
        binding.textViewQuote.text = quote.quote
        binding.textViewAuthor.text = quote.author
    }
}