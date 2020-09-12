package com.fabirt.roka.features.detail.presentation.view_model

import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.error.Failure

sealed class RecipeDetailState {
    abstract val recipe: RecipeInformationModel

    data class Loading(override val recipe: RecipeInformationModel) : RecipeDetailState()

    data class Error(
        override val recipe: RecipeInformationModel,
        val failure: Failure
    ) : RecipeDetailState()

    data class Success(override val recipe: RecipeInformationModel) : RecipeDetailState()
}