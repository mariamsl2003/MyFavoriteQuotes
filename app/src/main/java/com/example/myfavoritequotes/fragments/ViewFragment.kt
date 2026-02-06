package com.example.myfavoritequotes.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfavoritequotes.R
import com.example.myfavoritequotes.base.datastore.IClickListener
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.databinding.FragmentViewBinding
import com.example.myfavoritequotes.recyclerView.adapter.QuoteAdapter
import com.example.myfavoritequotes.viewModel.ViewViewModel
import kotlinx.coroutines.launch

class ViewFragment : Fragment(), IClickListener {

    private lateinit var binding: FragmentViewBinding

    private val viewModel: ViewViewModel by lazy {
        ViewViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData(requireContext())
        setViews()
    }

    fun setViews(){
        //setting the recycler view
        binding.recyclerViewQuotes.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.VERTICAL,
            false)
        setObserves()
    }

    fun setObserves(){
        viewModel.liveQuotes().observe(this){quotes ->
            binding.recyclerViewQuotes.adapter = QuoteAdapter(quotes, requireContext()).apply {
                inter = this@ViewFragment
            }

        }
    }

    override fun onFavoriteClick(quote: Quotes) {
        lifecycleScope.launch {
            val id = quote.id
            if(viewModel.isFavorite(requireContext(), id)){
                //already a favorite, remove favorite and tell them
                viewModel.unsetFavorite(requireContext(), id)
                Log.d("DEBUG", "in onFavoriteClick, isFavorite is true, id = $id")
                Toast.makeText(requireContext(), getString(R.string.remove_favorite), LENGTH_SHORT).show()
            }else{
                //not a favorite
                if(viewModel.canBeFavorite(requireContext())){
                    Log.d("DEBUG", "in onFavoriteClick, canBeFavorite is true, id = $id")
                    //there is already a favorite, can't be one
                    Toast.makeText(requireContext(), getString(R.string.can_not_be_favorite), LENGTH_SHORT).show()
                }else{
                    //there is no favorite, can be one
                    viewModel.setFavorite(requireContext(), id)
                    Log.d("DEBUG", "in onFavoriteClick, both isFavorite and canBeFavorite are false, id = $id")
                    Toast.makeText(requireContext(), getString(R.string.become_favorite), LENGTH_SHORT).show()
                }
            }
        }

    }
}