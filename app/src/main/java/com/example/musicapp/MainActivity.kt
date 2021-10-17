package com.example.musicapp

import android.content.SharedPreferences
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import androidx.work.*
import com.example.musicapp.databinding.ActivityMainBinding
import com.example.musicapp.services.TAG
import com.example.musicapp.workmanager.FindReleaseWorker
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var binding: ActivityMainBinding
    private var theme: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        theme =
            PreferenceManager.getDefaultSharedPreferences(application).getString("theme", "Light")
                ?: "Light"

        when(theme){
            "Light" -> setTheme(R.style.Theme_MusicApp)
            "Classic" -> setTheme(R.style.Theme_MusicApp_Classic)
            else -> setTheme(R.style.Theme_MusicApp)
        }
        setContentView(binding.root)
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

    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            Log.i(TAG, "getToken: $token")
        })
    }

    private fun initWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()
        val request = PeriodicWorkRequestBuilder<FindReleaseWorker>(3, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            WORKER,
            ExistingPeriodicWorkPolicy.KEEP, request
        )
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key ) {
            "theme" -> {
                theme =
                    PreferenceManager.getDefaultSharedPreferences(application)
                        .getString(key, "Light") ?: "Light"
                setTheme(theme)
            }
            "release_switch" -> {
                val sendRelease = PreferenceManager.getDefaultSharedPreferences(application)
                    .getBoolean(key, true)
                if (sendRelease)
                    initWorker()
                else WorkManager.getInstance(applicationContext).cancelUniqueWork(WORKER)
            }
        }
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val themeName = this.theme ?: PreferenceManager.getDefaultSharedPreferences(application)
            .getString("theme", "Light") ?: "Light"
        when (themeName) {
            "Light" -> theme.applyStyle(R.style.Theme_MusicApp, true)
            "Classic" -> theme.applyStyle(R.style.Theme_MusicApp_Classic, true)
            else -> theme.applyStyle(R.style.Theme_MusicApp, true)
        }
        return theme
    }

    private fun setTheme(themeName: String?) {
        val preferences =
            PreferenceManager.getDefaultSharedPreferences(application)
        preferences.edit().apply{
            putString("theme", themeName)
            apply()
        }
        recreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG, "onDestroy: activity")
    }

    companion object{
        const val WORKER = "release_work"
    }

}

const val LOG = "my_logs"