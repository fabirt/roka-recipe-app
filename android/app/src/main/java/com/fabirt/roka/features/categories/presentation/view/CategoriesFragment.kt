package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.features.categories.domain.model.CategoryItem
import com.fabirt.roka.features.categories.presentation.adapters.CategoriesAdapter
import com.fabirt.roka.features.categories.presentation.dispatchers.CategoryEventDispatcher
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoriesViewModel
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.view_spin_indicator.*

class CategoriesFragment : Fragment(), CategoryEventDispatcher {

    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = CategoriesAdapter(this)
        exitTransition = Hold()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Prepare reenter transsition
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            spinView.visibility = View.GONE
            rvCategories.scheduleLayoutAnimation()
            adapter.submitList(categories)
        })
    }

    override fun onDestroyView() {
        rvCategories.adapter = null
        super.onDestroyView()
    }

    override fun onCategoryPressed(category: CategoryItem, view: View) {
        val extras = FragmentNavigatorExtras(view to category.name)
        val action = CategoriesFragmentDirections
            .actionCategoriesFragmentToCategoryDetailFragment(category)
        findNavController().navigate(action, extras)
    }
}