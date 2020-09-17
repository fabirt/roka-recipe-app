package com.fabirt.roka.features.favorites.presentation.dispatchers

import android.view.View
import com.fabirt.roka.core.domain.model.Recipe

interface FavoriteRecipeEventDispatcher {
    fun onFavoriteRecipePressed(recipe: Recipe, view: View)
}