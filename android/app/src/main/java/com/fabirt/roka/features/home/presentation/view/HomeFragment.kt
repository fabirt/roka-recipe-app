package com.fabirt.roka.features.home.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.setupWithNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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