package com.fabirt.roka.features.search.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.features.detail.presentation.view_model.DetailViewModel
import com.fabirt.roka.features.search.presentation.adapters.RecipeAdapter
import com.fabirt.roka.features.search.presentation.view_model.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.search_bar.*

class SearchFragment : Fragment() {
    private val viewModel: SearchViewModel by viewModels()
    private val detailViewModel: DetailViewModel by activityViewModels()
    private lateinit var adapter: RecipeAdapter

    companion object {
        const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecipeAdapter(listOf()) { recipe, image ->
            detailViewModel.setRecipeInfo(recipe)
            val action = SearchFragmentDirections.actionSearchFragmentToRecipeDetailFragment()
            val extras = FragmentNavigatorExtras(
                image to "recipeImage"
            )
            view.findNavController().navigate(action, extras)
        }
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

        btnCancelSearch.setOnClickListener {
            editTextSearch.text.clear()
        }

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            Log.i(TAG, recipes.toString())
            adapter.submitList(recipes)
        })

        viewModel.requestRecipes()
    }

}