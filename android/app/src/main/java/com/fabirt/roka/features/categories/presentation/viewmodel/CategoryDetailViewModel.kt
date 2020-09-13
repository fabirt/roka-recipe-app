package com.fabirt.roka.features.categories.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.features.categories.domain.model.CategoryItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CategoryDetailViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _category = MutableLiveData<CategoryItem>()
    val category: LiveData<CategoryItem>
        get() = _category

    private val _state = MutableLiveData<CategoryDetailState>()
    val state: LiveData<CategoryDetailState>
        get() = _state

    fun setCurrentCategory(categoryItem: CategoryItem) {
        _category.value = categoryItem
        requestRecipesForCategory(categoryItem)
    }

    private fun requestRecipesForCategory(categoryItem: CategoryItem) {
        viewModelScope.launch {
            _state.value = CategoryDetailState.Loading
            delay(2000)
        }
    }
}