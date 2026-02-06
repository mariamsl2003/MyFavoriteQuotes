package com.example.myfavoritequotes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.myfavoritequotes.R
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.databinding.FragmentAddBinding
import com.example.myfavoritequotes.viewModel.AddViewModel


class AddFragment : Fragment() {
  private lateinit var binding: FragmentAddBinding
  private val viewModel: AddViewModel by lazy {
      AddViewModel()
  }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.canBeFavorite(requireContext())
        setListeners()
        setObserves()
    }

    fun becomeFavorite() = binding.checkboxFavorite.isChecked

    fun setObserves(){
        viewModel.liveFavorite().observe(this){
            binding.checkboxFavorite.isEnabled = it
        }
    }

    fun setListeners(){
        binding.editTextQuote.addTextChangedListener {
            toggleButton()
        }
        binding.editTextAuthor.addTextChangedListener {
            toggleButton()
        }
        binding.addButtonSave.setOnClickListener {
            Log.d("DEBUG", "Button Clicked")
            val quote = Quotes()
            quote.quote = getQuote()
            quote.author = getAuthor()
            quote.favorite = getFavorite()
            viewModel.insert(quote, requireContext(), becomeFavorite())
            findNavController().popBackStack()
        }
    }

    fun toggleButton(){
        val quote = getQuote()
        val author = getAuthor()

        binding.addButtonSave.isEnabled = viewModel.toggleButton(quote, author)
    }

    fun getQuote() = binding.editTextQuote.text.toString()
    fun getAuthor() = binding.editTextAuthor.text.toString()
    fun getFavorite() = binding.checkboxFavorite.isEnabled
}