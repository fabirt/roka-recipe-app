package com.fabirt.roka.features.search.presentation.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.error.toFailure
import com.fabirt.roka.core.presentation.adapters.PagingLoadStateAdapter
import com.fabirt.roka.core.presentation.adapters.RecipePagingAdapter
import com.fabirt.roka.core.presentation.dispatchers.RecipeEventDispatcher
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.features.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), RecipeEventDispatcher {
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var pagingAdapter: RecipePagingAdapter

    companion object {
        const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pagingAdapter = RecipePagingAdapter(this)
        rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        rvRecipes.adapter = pagingAdapter.withLoadStateFooter(PagingLoadStateAdapter())
        setupListeners()
    }

    override fun onRecipePressed(recipe: Recipe, view: View) {
        dismissKeyboard(editTextSearch)
        navigateToRecipeDetail(recipe, view)
    }

    private fun setupListeners() {
        editTextSearch.addTextChangedListener { text ->
            btnCancelSearch.isVisible = !text.isNullOrEmpty()
        }

        editTextSearch.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                dismissKeyboard(v)
                requestSearch()
                return@setOnKeyListener true
            }
            false
        }

        btnCancelSearch.setOnClickListener {
            editTextSearch.text.clear()
        }

        rvRecipes.setOnScrollChangeListener { v, _, _, _, _ ->
            dismissKeyboard(v)
        }

        btnRetry.setOnClickListener {
            requestSearch(shouldRetry = true)
        }

        pagingAdapter.addLoadStateListener(::listenLoadStateChanges)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.recipesFlow.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun listenLoadStateChanges(loadStates: CombinedLoadStates) {
        val loadState = loadStates.source.refresh
        spinView.isVisible = loadState is LoadState.Loading
        rvRecipes.isVisible = loadState is LoadState.NotLoading && pagingAdapter.itemCount > 0
        emptyView.isVisible = loadState is LoadState.NotLoading && pagingAdapter.itemCount == 0
        errorView.isVisible = loadState is LoadState.Error
        if (loadState is LoadState.Error) {
            val failure = loadState.error.toFailure()
            tvErrorSubtitle.text = failure.translate(requireContext())
        }
    }

    private fun requestSearch(shouldRetry: Boolean = false) {
        val text = editTextSearch.text.toString()
        if (text.trim().isNotEmpty() || shouldRetry) {
            viewModel.query = text
            pagingAdapter.refresh()
        }
    }

    private fun dismissKeyboard(view: View) {
        val inputMethodManager = ContextCompat.getSystemService(
            requireContext(), InputMethodManager::class.java
        )
        view.clearFocus()
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}