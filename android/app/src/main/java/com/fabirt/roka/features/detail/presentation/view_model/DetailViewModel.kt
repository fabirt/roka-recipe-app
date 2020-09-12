package com.fabirt.roka.features.detail.presentation.view_model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.core.error.Failure
import kotlinx.coroutines.launch

class DetailViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _recipeInfo = MutableLiveData<RecipeInformationModel>()
    val recipeInfo: LiveData<RecipeInformationModel>
        get() = _recipeInfo

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _failure = MutableLiveData<Failure?>()
    val failure: LiveData<Failure?>
        get() = _failure

    fun setRecipeInfo(recipe: RecipeInformationModel) {
        viewModelScope.launch {
            _recipeInfo.value = recipe
            _failure.value = null
            _isLoading.value = true
            val result = repository.requestRecipeInformation(recipe.id)
            _isLoading.value = false
            result.fold(
                { failure ->
                    _failure.value = failure
                },
                { recipe ->
                    _recipeInfo.value = recipe
                }
            )
        }
    }
}