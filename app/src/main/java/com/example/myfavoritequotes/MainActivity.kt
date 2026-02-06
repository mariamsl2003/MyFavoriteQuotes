package com.example.myfavoritequotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myfavoritequotes.databinding.ActivityMainBinding
import com.example.myfavoritequotes.viewModel.MainViewModel
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkInt = runBlocking { viewModel.isDarkModeEnabled(this@MainActivity) }
        val isDark = isDarkInt == AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(isDarkInt)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        setViews()
        setListeners()
    }

    fun setListeners(){
        binding.imageButtonRandomQuote.setOnClickListener {
            findNavController(R.id.landing_fragment).navigate(R.id.random_quote_fragment)
        }
        binding.imageButtonSettings.setOnClickListener {
            findNavController(R.id.landing_fragment).navigate(R.id.settings_fragment)
        }
        binding.imageButtonView.setOnClickListener {
            findNavController(R.id.landing_fragment).navigate(R.id.view_fragment)
        }
        binding.imageButtonAdd.setOnClickListener {
            findNavController(R.id.landing_fragment).navigate(R.id.add_fragment)
        }
    }

    fun setViews(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.landing_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }
}