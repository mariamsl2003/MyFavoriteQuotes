package com.example.myfavoritequotes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myfavoritequotes.R
import com.example.myfavoritequotes.base.BClickListener
import com.example.myfavoritequotes.base.module.Quotes
import com.example.myfavoritequotes.base.utils.QuotesUtils
import com.example.myfavoritequotes.databinding.FragmentSettingsBinding
import com.example.myfavoritequotes.viewModel.SettingsViewModel
import android.Manifest
import android.os.Build
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(), BClickListener {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by lazy {
        SettingsViewModel()
    }
    lateinit var myQuotes: List<Quotes>

    private val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted && myQuotes.isNotEmpty()) {  // Ensure myQuotes is set
            QuotesUtils.exporting(requireContext(), myQuotes)
            Toast.makeText(requireContext(), getString(R.string.file_downloaded), LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.error), LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.getFavorite(requireContext())
        }
        setObserves()
        setListeners()
    }

    fun setListeners(){
        binding.buttonClear.setOnClickListener {
            viewModel.clearAllQuotes(requireContext())
            Toast.makeText(requireContext(), getString(R.string.quotes_cleared), LENGTH_SHORT).show()
            onStart()
        }
        binding.buttonExport.setOnClickListener {
            Log.d("DEBUG", "Button Clicked")
            viewLifecycleOwner.lifecycleScope.launch {
                myQuotes = viewModel.getAllQuotes(requireContext())
                if(myQuotes.isNotEmpty())
                    onExportClick(myQuotes)
                else
                    Toast.makeText(requireContext(), getString(R.string.no_quotes), LENGTH_SHORT).show()
            }
        }
        binding.buttonMode.setOnClickListener {
            lifecycleScope.launch {
                val currentMode = viewModel.isDarkModeEnabled(requireContext())
                val newMode = if(currentMode == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.MODE_NIGHT_NO
                }else{
                    AppCompatDelegate.MODE_NIGHT_YES
                }
                viewModel.setDarkMode(requireContext(), newMode)
                AppCompatDelegate.setDefaultNightMode(newMode)
            }
        }
    }

    fun setObserves(){
        viewModel.liveFavorite().observe(this){quotes ->
            Log.d("DEBUG", "Favorite observer triggered: quotes = $quotes")
            if (quotes != null){
                binding.textViewFavoriteQuote.text = quotes.quote
                binding.textViewFavoriteAuthor.text = quotes.author
            }else{
                binding.textViewFavoriteQuote.text = getString(R.string.no_favorite)
                binding.textViewFavoriteAuthor.text = getString(R.string.no_favorite)
            }
        }
    }

    override fun onExportClick(quotes: List<Quotes>) {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10+ doesn't need WRITE_EXTERNAL_STORAGE for MediaStore
            emptyArray<String>()
        } else {
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissions.isEmpty() || QuotesUtils.permissionsGranted(requireContext(), permissions)) {
            QuotesUtils.exporting(requireContext(), quotes)  // Pass context for MediaStore
            Toast.makeText(requireContext(), getString(R.string.file_downloaded), Toast.LENGTH_SHORT).show()
        } else {
            launcher.launch(permissions)
        }
    }
}