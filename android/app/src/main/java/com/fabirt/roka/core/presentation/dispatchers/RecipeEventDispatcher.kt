package com.fabirt.roka.core.presentation.dispatchers

import android.view.View
import com.fabirt.roka.core.domain.model.Recipe

interface RecipeEventDispatcher {
    fun onRecipePressed(recipe: Recipe, view: View)
}