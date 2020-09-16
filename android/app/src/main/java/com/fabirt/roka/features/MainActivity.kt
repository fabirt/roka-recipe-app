package com.fabirt.roka.features

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.fabirt.roka.R
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        const val APP_UPDATE_REQUEST_CODE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
        configureStatusBarForFullscreen()
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

    override fun onResume() {
        super.onResume()
        checkUpdate()
    }

    private fun checkUpdate() {
        Log.e("MainActivity", "checking")
        val updateManager = AppUpdateManagerFactory.create(this)
        val updateInfoTask = updateManager.appUpdateInfo
        updateInfoTask.addOnSuccessListener { updateInfo ->
            Log.i("MainActivity", updateInfo.updateAvailability().toString())
            val availability = updateInfo.availableVersionCode()
            if (availability == UpdateAvailability.UPDATE_AVAILABLE) {
                updateManager.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    APP_UPDATE_REQUEST_CODE
                )
            } else if (availability == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                updateManager.startUpdateFlowForResult(
                    updateInfo,
                    AppUpdateType.IMMEDIATE,
                    this,
                    APP_UPDATE_REQUEST_CODE
                )
            }
        }.addOnFailureListener { error ->
            Log.e("MainActivity", error.toString())
        }
    }

    /*
    Dynamic start destination
    private fun setupNavigation() {
        val navController = Navigation.findNavController(this, R.id.mainNavHostFragment)
        val navGraph = navController.navInflater.inflate(R.navigation.main_graph)
        lifecycleScope.launch {
            val didShow = dataStoreViewModel.readOnboardingDidShow()
            var startDestination = R.id.onboardingFragment
            if (didShow) {
                startDestination = R.id.homeFragment
            }
            navGraph.startDestination = startDestination
            navController.graph = navGraph
        }
    }

     */
}