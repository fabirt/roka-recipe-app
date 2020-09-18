package com.fabirt.roka.features.favorites.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabirt.roka.core.domain.repository.RecipeRepository

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
}