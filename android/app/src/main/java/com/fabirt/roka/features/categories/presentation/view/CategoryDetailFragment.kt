package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.applyTopWindowInsets
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailState
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailViewModel
import com.fabirt.roka.features.search.presentation.adapters.RecipeAdapter
import kotlinx.android.synthetic.main.fragment_category_detail.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*

class CategoryDetailFragment : Fragment() {
    private val viewModel: CategoryDetailViewModel by activityViewModels()
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar(false)
        return inflater.inflate(R.layout.fragment_category_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.applyTopWindowInsets()

        adapter = RecipeAdapter(listOf()) { recipe, _ ->
            navigateToRecipeDetail(recipe)
        }
        rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        rvRecipes.adapter = adapter

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnRetry.setOnClickListener {
            viewModel.requestRecipesForCategory(viewModel.category.value!!)
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.category.observe(viewLifecycleOwner, Observer { category ->
            tvTitle.text = category.name
            bindNetworkImage(ivCategoryItem, category.imageUrl)
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                CategoryDetailState.Loading -> {
                    buildLoading()
                }
                is CategoryDetailState.Success -> {
                    buildSuccess(state)
                }
                is CategoryDetailState.Error -> {
                    buildFailure(state)
                }
            }
        })
    }

    private fun buildLoading() {
        spinView.visibility = View.VISIBLE
        rvRecipes.visibility = View.GONE
        errorView.visibility = View.GONE
    }

    private fun buildSuccess(state: CategoryDetailState.Success) {
        spinView.visibility = View.GONE
        rvRecipes.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        rvRecipes.scheduleLayoutAnimation()
        adapter.submitList(state.recipes)
    }

    private fun buildFailure(state: CategoryDetailState.Error) {
        spinView.visibility = View.GONE
        rvRecipes.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        tvErrorSubtitle.text = state.failure.toString()
    }
}