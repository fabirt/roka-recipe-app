package com.fabirt.roka.features.detail.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class RecipeDetailViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableLiveData<RecipeDetailState>()
    val state: LiveData<RecipeDetailState>
        get() = _state

    private var isFavorite = false

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

    fun saveOrDeleteRecipe() {
        if (isFavorite) {
            deleteFavoriteRecipe()
        } else {
            saveFavoriteRecipe()
        }

    }

    fun isFavorite(id: Int): LiveData<Boolean> {
        return repository.requestFavoriteRecipeById(id).map {
            isFavorite = it != null
            isFavorite
        }.asLiveData()
    }

    private fun saveFavoriteRecipe() {
        viewModelScope.launch {
            _state.value?.let { state ->
                if (state is RecipeDetailState.Success) {
                    repository.saveFavoriteRecipe(state.recipe)
                }
            }
        }
    }

    private fun deleteFavoriteRecipe() {
        viewModelScope.launch {
            _state.value?.recipe?.let { repository.deleteFavoriteRecipe(it) }
        }
    }
}