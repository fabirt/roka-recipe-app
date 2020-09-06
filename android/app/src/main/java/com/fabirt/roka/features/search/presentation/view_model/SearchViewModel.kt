package com.fabirt.roka.features.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.core.domain.repository.RecipeRepositoryImpl
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val repository: RecipeRepository

    private val _recipes = MutableLiveData<List<RecipeInformationModel>>()
    val recipes: LiveData<List<RecipeInformationModel>>
        get() = _recipes

    private val _isSearching = MutableLiveData(true)
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    init {
        val service = RecipesApiClient.getRecipeService()
        repository = RecipeRepositoryImpl(service)
    }

    init {
        requestRecipes()
    }

    fun requestRecipes(query: String = "") {
        viewModelScope.launch {
            _isSearching.value = true
            val result = repository.searchRecipes(query, true)
            _isSearching.value = false
            result.fold(
                { failure ->
                    Log.e("requestRecipes", failure.toString())
                    _recipes.value = listOf()
                },
                { recipes ->
                    _recipes.value = recipes
                }
            )
        }
    }
}