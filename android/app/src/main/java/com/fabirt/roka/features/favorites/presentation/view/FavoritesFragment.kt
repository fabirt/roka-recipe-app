package com.fabirt.roka.features.favorites.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fabirt.roka.MainGraphDirections
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.findMainNavController
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailViewModel
import com.fabirt.roka.features.favorites.presentation.adapters.FavoritesAdapter
import com.fabirt.roka.features.favorites.presentation.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()
    private val detailViewModel: RecipeDetailViewModel by activityViewModels()
    private lateinit var adapter: FavoritesAdapter

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoritesAdapter(::openRecipeDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvFavorites.layoutManager = layoutManager
        rvFavorites.adapter = adapter
        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            adapter.submitList(recipes)
        })
    }

    private fun openRecipeDetail(recipe: Recipe) {
        detailViewModel.presentRecipeInfo(recipe)
        val action = MainGraphDirections.actionGlobalRecipeDetailFragment()
        findMainNavController().navigate(action)
    }
}