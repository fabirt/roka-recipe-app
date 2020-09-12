package com.fabirt.roka.features.detail.presentation.view_model

import com.fabirt.roka.core.data.network.model.NetworkRecipe
import com.fabirt.roka.core.error.Failure

sealed class RecipeDetailState {
    abstract val recipe: NetworkRecipe

    data class Loading(override val recipe: NetworkRecipe) : RecipeDetailState()

    data class Error(
        override val recipe: NetworkRecipe,
        val failure: Failure
    ) : RecipeDetailState()

    data class Success(override val recipe: NetworkRecipe) : RecipeDetailState()
}