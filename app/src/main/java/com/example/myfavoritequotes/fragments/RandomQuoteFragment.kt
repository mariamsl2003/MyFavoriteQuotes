package com.example.myfavoritequotes.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.example.myfavoritequotes.R
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.databinding.FragmentRandomQuoteBinding
import com.example.myfavoritequotes.viewModel.RandomQuoteViewModel

class RandomQuoteFragment : Fragment() {
    private lateinit var binding: FragmentRandomQuoteBinding
    private val viewModel: RandomQuoteViewModel by lazy {
        RandomQuoteViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomQuoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getRandomQuote()
        setObserves()
        setListeners()
    }

    fun getQuote() = binding.textViewRandomQuote.text.toString()
    fun getAuthor() = binding.textViewRandomAuthor.text.toString()

    fun setListeners(){
        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.buttonSave.setOnClickListener {
            val quote = Quotes()
            quote.quote = getQuote()
            quote.author = getAuthor()
            viewModel.insert(quote, requireContext())
            Toast.makeText(requireContext(), getString(R.string.saving), LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }

    fun setObserves(){
        viewModel.liveRandomQuote().observe(viewLifecycleOwner){ quotes ->
            if(quotes != null){
                Log.d("DEBUG", "In live data observe: quote is ${quotes.quote} and author is ${quotes.author}")
                binding.textViewRandomQuote.text = quotes?.quote
                binding.textViewRandomAuthor.text = quotes?.author
                binding.buttonSave.isEnabled = true
            }
        }
    }
}