package com.fabirt.roka.features.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val service = RecipesApiClient.getRecipeService()

    private val _recipes = MutableLiveData<List<RecipeInformationModel>>()
    val recipes: LiveData<List<RecipeInformationModel>>
        get() = _recipes

    fun requestRecipes() {
        if (_recipes.value?.isNotEmpty() == true) return
        viewModelScope.launch {
            try {
                val result = service.searchRecipes("pasta", true)
                _recipes.value = result.results
            } catch (e: Exception) {
                Log.e("SearchViewModel", e.toString())
            }
        }
    }
}