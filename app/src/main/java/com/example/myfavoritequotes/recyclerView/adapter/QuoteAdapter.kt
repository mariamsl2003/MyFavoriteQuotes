package com.example.myfavoritequotes.recyclerView.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.RecyclerView
import com.example.myfavoritequotes.base.datastore.IClickListener
import com.example.myfavoritequotes.databinding.ViewHolderQuotesBinding
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.recyclerView.viewHolder.QuotesViewHolder
import com.example.myfavoritequotes.viewModel.ViewViewModel

class QuoteAdapter(val quotes: List<Quotes>, val context: Context): RecyclerView.Adapter<QuotesViewHolder>() {

    var inter : IClickListener ?= null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuotesViewHolder {
        val binding = ViewHolderQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: QuotesViewHolder,
        position: Int
    ) {
        holder.bind(quotes[position])

        holder.binding.imageButtonFavorite.setOnClickListener {
            inter?.onFavoriteClick(quotes[position])
            val id = quotes[position].id
            Log.d("DEBUG", "in onBindViewHolder, id = $id")
        }
    }

    override fun getItemCount(): Int {
        return quotes.size
    }
}