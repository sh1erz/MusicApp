package com.example.musicapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.ui.main.model.LOG
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.i(LOG, "onCreate: activity")

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.navHostFragment).apply {
            addOnDestinationChangedListener { _, _, args ->
                if (args?.getBoolean("ShowNavBar") == false) {
                    navView.visibility = View.GONE
                } else navView.visibility = View.VISIBLE
            }
        }
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.settingsFragment,
                R.id.mainFragment,
                R.id.searchFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG, "onDestroy: activity")
    }

}