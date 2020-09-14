package com.fabirt.roka.features.categories.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.features.categories.domain.model.CategoryItem
import kotlinx.coroutines.launch

class CategoryDetailViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private var parent: String? = null

    private val _category = MutableLiveData<CategoryItem>()
    val category: LiveData<CategoryItem>
        get() = _category

    private val _state = MutableLiveData<CategoryDetailState>()
    val state: LiveData<CategoryDetailState>
        get() = _state

    fun setCurrentCategory(parent: String, categoryItem: CategoryItem) {
        this.parent = parent
        _category.value = categoryItem
        requestRecipesForCategory(categoryItem)
    }

    fun requestRecipesForCategory(categoryItem: CategoryItem) {
        viewModelScope.launch {
            _state.value = CategoryDetailState.Loading
            val options = mapOf(parent!! to categoryItem.name)
            val result = repository.searchRecipes(
                addRecipeInformation = true,
                number = K.RECIPES_PER_PAGE,
                offset = 0,
                options = options
            )
            result.fold(
                { failure ->
                    _state.value = CategoryDetailState.Error(failure)
                }, { recipes ->
                    _state.value = CategoryDetailState.Success(recipes)
                }
            )
        }
    }
}