package com.fabirt.roka.features.favorites.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.core.utils.showDialog
import com.fabirt.roka.features.favorites.domain.model.FavoriteRecipe
import com.fabirt.roka.features.favorites.presentation.adapters.FavoritesAdapter
import com.fabirt.roka.features.favorites.presentation.dispatchers.FavoriteRecipeEventDispatcher
import com.fabirt.roka.features.favorites.presentation.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.view_empty.*

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoriteRecipeEventDispatcher {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter
    private var favoriteRecipes = mutableListOf<FavoriteRecipe>()
    private lateinit var onBackPressedCallback: OnBackPressedCallback

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoritesAdapter(this, false)
        onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            viewModel.changeSelecting(false)
            resetFavorites()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvFavorites.layoutManager = layoutManager
        rvFavorites.adapter = adapter

        btnTrash.setOnClickListener {
            showDeleteDialog()
        }

        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes.isEmpty()) {
                rvFavorites.visibility = View.INVISIBLE
                emptyTextView.text = getString(R.string.no_favorites)
                emptyImageView.setImageResource(R.drawable.ic_favorites)
                emptyView.visibility = View.VISIBLE
            } else {
                rvFavorites.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                favoriteRecipes = recipes
                    .map { FavoriteRecipe(it, false) }
                    .toMutableList()
                adapter.submitList(favoriteRecipes)
            }
        })

        viewModel.isSelecting.observe(viewLifecycleOwner, Observer { isSelecting ->
            btnTrash.isVisible = isSelecting
            adapter.isSelecting = isSelecting
            onBackPressedCallback.isEnabled = isSelecting
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        resetFavorites()
        viewModel.changeSelecting(false)
    }

    override fun onFavoriteRecipePressed(recipe: FavoriteRecipe, view: View) {
        if (viewModel.isSelecting.value == true) {
            val index = favoriteRecipes.indexOf(recipe)
            favoriteRecipes[index] = recipe.copy(isSelected = !recipe.isSelected)
            adapter.notifyItemChanged(index)
        } else {
            navigateToRecipeDetail(recipe.data, view, isFavorite = true)
        }
    }

    override fun onFavoriteRecipeLongPressed(recipe: FavoriteRecipe) {
        if (viewModel.isSelecting.value == false) {
            val index = favoriteRecipes.indexOf(recipe)
            favoriteRecipes[index] = recipe.copy(isSelected = true)
            viewModel.changeSelecting(true)
        }
    }

    private fun resetFavorites() {
        favoriteRecipes = favoriteRecipes.map { it.copy(isSelected = false) }.toMutableList()
        adapter.submitList(favoriteRecipes)
    }

    private fun showDeleteDialog() {
        val recipesToDelete = favoriteRecipes.filter { it.isSelected }
        if (recipesToDelete.isEmpty()) return
        val message = getString(R.string.delete_favorites_message, recipesToDelete.size)
        showDialog(
            titleId = R.string.delete,
            message = message,
            positiveTextId = R.string.delete,
            negativeTextId = R.string.cancel,
            onCancel = null,
            onConfirm = {
                deleteFavorites(recipesToDelete)
            }
        )
    }

    private fun deleteFavorites(favorites: List<FavoriteRecipe>) {
        viewModel.changeSelecting(false)
        viewModel.deleteFavorites(favorites)
    }
}