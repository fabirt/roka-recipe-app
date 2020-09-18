package com.fabirt.roka.features.favorites.presentation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.fabirt.roka.features.favorites.domain.model.FavoriteRecipe
import com.fabirt.roka.features.favorites.presentation.dispatchers.FavoriteRecipeEventDispatcher
import com.fabirt.roka.features.favorites.presentation.viewholders.FavoriteRecipeViewHolder

class FavoritesAdapter(
    private val eventDispatcher: FavoriteRecipeEventDispatcher
) : ListAdapter<FavoriteRecipe, FavoriteRecipeViewHolder>(FavoriteRecipeViewHolder.FavoriteRecipeComparator) {
    private var _isSelecting = false
    var isSelecting: Boolean
        get() = _isSelecting
        set(value) {
            _isSelecting = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder {
        return FavoriteRecipeViewHolder.from(parent, eventDispatcher)
    }

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe, isSelecting)
    }
}