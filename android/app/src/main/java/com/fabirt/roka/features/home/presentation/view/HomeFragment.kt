package com.fabirt.roka.features.home.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.setupWithNavController
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var isPresented = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("HomeFragment", "ON_CREATE_CALLED")
        val transitionDuration = resources.getInteger(R.integer.page_transition_duration)
        exitTransition = Hold().apply {
            duration = transitionDuration.toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isPresented) {
            // Prepare reenter transsition
            postponeEnterTransition()
            view.doOnPreDraw { startPostponedEnterTransition() }
        }
        isPresented = true

        if (savedInstanceState == null) {
            setupBottomNavigation()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val activity = requireActivity()
        val navGraphIds = listOf(
            R.navigation.categories_graph,
            R.navigation.search_graph,
            R.navigation.favorites_graph
        )
        bottomNav.setupWithNavController(
            navGraphIds,
            childFragmentManager,
            R.id.homeNavHostContainer,
            activity.intent
        )
    }
}