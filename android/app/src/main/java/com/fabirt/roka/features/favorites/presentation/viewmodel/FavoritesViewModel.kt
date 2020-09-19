package com.fabirt.roka.features.favorites.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.features.favorites.domain.model.FavoriteRecipe
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _isSelecting = MutableLiveData(false)
    val isSelecting: LiveData<Boolean>
        get() = _isSelecting

    val recipes = repository.requestFavoriteRecipes().asLiveData()

    fun changeSelecting(value: Boolean) {
        _isSelecting.value = value
    }

    fun deleteFavorites(favorites: List<FavoriteRecipe>) {
        viewModelScope.launch {
            for (fav in favorites) {
                repository.deleteFavoriteRecipe(fav.data)
            }
        }
    }
}