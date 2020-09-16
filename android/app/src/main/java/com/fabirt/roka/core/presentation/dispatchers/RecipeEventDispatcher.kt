package com.fabirt.roka.core.presentation.dispatchers

import com.fabirt.roka.core.domain.model.Recipe

interface RecipeEventDispatcher {
    fun onRecipePressed(recipe: Recipe)
}