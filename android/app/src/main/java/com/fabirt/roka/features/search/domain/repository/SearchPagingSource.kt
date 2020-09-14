package com.fabirt.roka.features.search.domain.repository

import androidx.paging.PagingSource
import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.data.network.model.asDomainModel
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.domain.model.Recipe

class SearchPagingSource(
    private val service: RecipeService,
    private val query: String
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        try {
            val nextPageNumber = params.key ?: 1
            val number = K.RECIPES_PER_PAGE
            val offset = (nextPageNumber - 1) * number
            val response = service.searchRecipes(
                query = query,
                addRecipeInformation = true,
                number = number,
                offset = offset
            )
            return LoadResult.Page(
                data = response.results.map { it.asDomainModel() },
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}