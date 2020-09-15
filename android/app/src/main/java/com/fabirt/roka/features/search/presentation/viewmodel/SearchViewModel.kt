package com.fabirt.roka.features.search.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.features.search.domain.repository.SearchPagingSource
import kotlinx.coroutines.flow.Flow

class SearchViewModel @ViewModelInject constructor(
    private val service: RecipeService
) : ViewModel() {

    val recipesFlow: Flow<PagingData<Recipe>>
    var query = ""

    init {
        recipesFlow = Pager(
            config = PagingConfig(K.RECIPES_PER_PAGE),
            pagingSourceFactory = ::createPagingSource
        ).flow
            .cachedIn(viewModelScope)
    }

    private fun createPagingSource(): SearchPagingSource {
        return SearchPagingSource(service, query)
    }
}