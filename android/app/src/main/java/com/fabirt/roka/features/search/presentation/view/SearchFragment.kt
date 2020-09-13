package com.fabirt.roka.features.search.presentation.view

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailViewModel
import com.fabirt.roka.features.search.presentation.adapters.RecipeAdapter
import com.fabirt.roka.features.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_bar.*
import kotlinx.android.synthetic.main.view_empty.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val detailViewModel: RecipeDetailViewModel by activityViewModels()
    private lateinit var adapter: RecipeAdapter

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
        adapter = RecipeAdapter(listOf(), ::openRecipeDetail)
        rvRecipes.layoutManager = LinearLayoutManager(requireContext())
        rvRecipes.adapter = adapter
        setupListeners()
    }

    private fun setupListeners() {
        editTextSearch.addTextChangedListener { text ->
            if (text.isNullOrEmpty()) {
                btnCancelSearch.visibility = View.INVISIBLE
            } else {
                btnCancelSearch.visibility = View.VISIBLE
            }
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

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes.isEmpty()) {
                rvRecipes.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                rvRecipes.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                rvRecipes.scheduleLayoutAnimation()
                adapter.submitList(recipes)
            }
        })

        viewModel.isSearching.observe(viewLifecycleOwner, Observer { isSearching ->
            if (isSearching) {
                rvRecipes.visibility = View.GONE
                spinView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                rvRecipes.layoutManager?.scrollToPosition(0)
            } else {
                rvRecipes.visibility = View.VISIBLE
                spinView.visibility = View.GONE
            }
        })

        viewModel.failure.observe(viewLifecycleOwner, Observer { failure ->
            if (failure != null) {
                rvRecipes.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                tvErrorSubtitle.text = failure.toString()
            } else {
                rvRecipes.visibility = View.VISIBLE
                errorView.visibility = View.GONE
            }
        })
    }

    private fun requestSearch(shouldRetry: Boolean = false) {
        val text = editTextSearch.text.toString()
        if (text.isNotEmpty() || shouldRetry) {
            viewModel.requestRecipes(text)
        }
    }

    private fun openRecipeDetail(recipe: Recipe, image: ImageView) {
        detailViewModel.requestRecipeInfo(recipe)
        dismissKeyboard(editTextSearch)
        navigateToRecipeDetail()
    }

    private fun dismissKeyboard(view: View) {
        val inputMethodManager = ContextCompat.getSystemService(
            requireContext(), InputMethodManager::class.java
        )
        view.clearFocus()
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }
}