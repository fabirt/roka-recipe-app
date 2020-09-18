package com.fabirt.roka.features.favorites.presentation.dispatchers

import android.view.View
import com.fabirt.roka.features.favorites.domain.model.FavoriteRecipe

interface FavoriteRecipeEventDispatcher {
    fun onFavoriteRecipePressed(recipe: FavoriteRecipe, view: View)

    fun onFavoriteRecipeLongPressed(recipe: FavoriteRecipe)
}