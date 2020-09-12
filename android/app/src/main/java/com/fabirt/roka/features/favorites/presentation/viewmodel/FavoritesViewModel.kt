package com.fabirt.roka.features.favorites.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.fabirt.roka.core.domain.repository.RecipeRepository

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    val recipes = repository.requestFavoriteRecipes().asLiveData()
}