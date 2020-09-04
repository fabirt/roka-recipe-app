package com.fabirt.roka.features.detail.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fabirt.roka.core.data.network.model.RecipeInformationModel

class DetailViewModel : ViewModel() {

    private val _recipeInfo = MutableLiveData<RecipeInformationModel>()
    val recipeInfo: LiveData<RecipeInformationModel>
        get() = _recipeInfo

    fun setRecipeInfo(recipe: RecipeInformationModel) {
        _recipeInfo.value = recipe
    }
}