package com.virginmoney.virginmoneydirectory.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.virginmoney.virginmoneydirectory.R
import com.virginmoney.virginmoneydirectory.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    lateinit var appbar: AppBarLayout
    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNavigationView
        appbar = binding.appBar
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val appConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_contact,
                R.id.navigation_room
            )
        )

        navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, appConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_contact,
                R.id.navigation_room -> {
                    navView.visibility = View.VISIBLE
//                    appbar.visibility = View.GONE
                }

                else -> {
                    navView.visibility = View.GONE
//                    appbar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}