package com.fabirt.roka.features.categories.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.*
import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.features.categories.domain.model.CategoryItem
import com.fabirt.roka.features.categories.domain.repository.FilteredRecipesPagingSource
import kotlinx.coroutines.flow.Flow

class CategoryDetailViewModel @ViewModelInject constructor(
    private val service: RecipeService
) : ViewModel() {

    var recipesFlow: Flow<PagingData<Recipe>>? = null

    private val _category = MutableLiveData<CategoryItem>()
    val category: LiveData<CategoryItem>
        get() = _category

    fun setCurrentCategory(categoryItem: CategoryItem) {
        _category.value = categoryItem
        requestRecipesForCategory(categoryItem)
    }

    private fun requestRecipesForCategory(categoryItem: CategoryItem) {
        recipesFlow = Pager(PagingConfig(K.RECIPES_PER_PAGE)) {
            val options = mapOf(categoryItem.type to categoryItem.name)
            FilteredRecipesPagingSource(service, options)
        }.flow
            .cachedIn(viewModelScope)
    }
}