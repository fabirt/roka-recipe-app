package com.fabirt.roka.features.detail.presentation.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.domain.repository.RecipeRepository
import kotlinx.coroutines.launch

class RecipeDetailViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData<RecipeDetailState>()
    val state: LiveData<RecipeDetailState>
        get() = _state

    fun requestRecipeInfo(recipe: Recipe) {
        viewModelScope.launch {
            _state.value = RecipeDetailState.Loading(recipe)
            val result = repository.requestRecipeInformation(recipe.id)
            val newState = result.fold(
                { failure ->
                    RecipeDetailState.Error(recipe, failure)
                },
                { recipe ->
                    RecipeDetailState.Success(recipe)
                }
            )
            _state.value = newState
        }
    }

    fun retryRecipeRequest() {
        _state.value?.recipe?.let {
            requestRecipeInfo(it)
        }
    }
}