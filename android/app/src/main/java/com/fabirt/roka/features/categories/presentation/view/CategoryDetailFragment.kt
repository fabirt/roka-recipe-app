package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.error.toFailure
import com.fabirt.roka.core.presentation.adapters.PagingLoadStateAdapter
import com.fabirt.roka.core.presentation.adapters.RecipePagingAdapter
import com.fabirt.roka.core.presentation.dispatchers.RecipeEventDispatcher
import com.fabirt.roka.core.utils.applyTopWindowInsets
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_category_detail.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoryDetailFragment : Fragment(), RecipeEventDispatcher {
    private val viewModel: CategoryDetailViewModel by activityViewModels()
    private val args: CategoryDetailFragmentArgs by navArgs()
    private lateinit var adapter: RecipePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = RecipePagingAdapter(this)
        viewModel.requestRecipesForCategory(args.category)
        configureTransitions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar(false)
        return inflater.inflate(R.layout.fragment_category_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rootView.transitionName = args.category.name

        btnBack.applyTopWindowInsets()

        rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        rvRecipes.adapter = adapter.withLoadStateFooter(PagingLoadStateAdapter())

        tvTitle.text = args.category.name
        bindNetworkImage(ivCategoryItem, args.category.imageUrl)

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        btnRetry.setOnClickListener {
            adapter.refresh()
        }

        setupObservers()
    }

    override fun onRecipePressed(recipe: Recipe) {
        navigateToRecipeDetail(recipe)
    }

    private fun setupObservers() {
        adapter.addLoadStateListener { loadStates ->
            val loadState = loadStates.source.refresh
            spinView.isVisible = loadState is LoadState.Loading
            rvRecipes.isVisible = loadState is LoadState.NotLoading && adapter.itemCount > 0
            errorView.isVisible = loadState is LoadState.Error
            if (loadState is LoadState.Error) {
                val failure = loadState.error.toFailure()
                tvErrorSubtitle.text = failure.translate(requireContext())
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow?.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun configureTransitions() {
        val color = requireContext().getColor(R.color.colorBackground)
        val transition = MaterialContainerTransform().apply {
            duration = 1000
            containerColor = color
            drawingViewId = R.id.homeNavHostContainer
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}