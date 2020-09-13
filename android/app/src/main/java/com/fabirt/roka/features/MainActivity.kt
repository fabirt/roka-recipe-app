package com.fabirt.roka.features

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.preferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.fabirt.roka.R
import com.fabirt.roka.core.constants.K
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        configureStatusBarForFullscreen()
        setupNavigation()
    }

    private fun configureStatusBarForFullscreen() {
        var flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }

        window.decorView.systemUiVisibility = flags
    }

    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.mainNavHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        lifecycleScope.launch {
            val dataStore = createDataStore(name = K.SETTINGS_DATA_STORE_NAME)
            val prefKey = preferencesKey<Boolean>(K.ONBOARDING_DID_SHOW_KEY)
            val onBoardingDidShowFlow = dataStore.data.map { preferences ->
                preferences[prefKey] ?: false
            }
            val didShow = onBoardingDidShowFlow.first()
            var startDestination = R.id.onboardingFragment
            if (didShow) {
                startDestination = R.id.homeFragment
            }
            navGraph.startDestination = startDestination
            navController.graph = navGraph
        }
    }
}