package com.fabirt.roka.features.categories.presentation.viewmodel

import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.error.Failure

sealed class CategoryDetailState {
    object Loading : CategoryDetailState()
    data class Success(val recipes: List<Recipe>) : CategoryDetailState()
    data class Error(val failure: Failure) : CategoryDetailState()
}