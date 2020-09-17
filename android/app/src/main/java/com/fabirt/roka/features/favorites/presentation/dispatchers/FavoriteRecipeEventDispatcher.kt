package com.fabirt.roka.features.favorites.presentation.dispatchers

import com.fabirt.roka.core.domain.model.Recipe

interface FavoriteRecipeEventDispatcher {
    fun onFavoriteRecipePressed(recipe: Recipe)
}