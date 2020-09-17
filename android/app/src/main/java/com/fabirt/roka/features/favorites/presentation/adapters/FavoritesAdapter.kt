package com.fabirt.roka.features.favorites.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.features.favorites.presentation.dispatchers.FavoriteRecipeEventDispatcher
import com.fabirt.roka.features.favorites.presentation.viewholders.FavoriteRecipeViewHolder

class FavoritesAdapter(
    private val eventDispatcher: FavoriteRecipeEventDispatcher
) : ListAdapter<Recipe, FavoriteRecipeViewHolder>(FavoriteRecipeViewHolder.FavoriteRecipeComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        return FavoriteRecipeViewHolder.from(parent, eventDispatcher)
    }

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }
}