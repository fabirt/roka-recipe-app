package com.fabirt.roka.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navGraphIds = listOf(
            R.navigation.categories_graph,
            R.navigation.search_graph,
            R.navigation.favorites_graph
        )
        bottomNav.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.mainNavHostFragment,
            intent
        )
    }
}