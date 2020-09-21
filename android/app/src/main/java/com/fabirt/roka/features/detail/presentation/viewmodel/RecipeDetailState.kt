package com.fabirt.roka.features.detail.presentation.viewmodel

import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.error.Failure

sealed class RecipeDetailState {
    abstract val recipe: Recipe?

    data class Loading(override val recipe: Recipe?) : RecipeDetailState()

    data class Error(
        override val recipe: Recipe?,
        val failure: Failure
    ) : RecipeDetailState()

    data class Success(override val recipe: Recipe?) : RecipeDetailState()
}