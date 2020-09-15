package com.fabirt.roka.features.search.domain.repository

import androidx.paging.PagingSource
import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.data.network.model.asDomainModel
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.error.HttpLimitExceededException
import retrofit2.HttpException

class SearchPagingSource(
    private val service: RecipeService,
    private val query: String
) : PagingSource<Int, Recipe>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        try {
            val pageNumber = params.key ?: 1
            val pageSize = K.RECIPES_PER_PAGE
            val offset = (pageNumber - 1) * pageSize
            val response = service.searchRecipes(
                query = query,
                addRecipeInformation = true,
                number = pageSize,
                offset = offset
            )
            val nextPageNumber = if (response.totalResults - pageSize > offset && offset < 900) {
                pageNumber + 1
            } else {
                null
            }
            return LoadResult.Page(
                data = response.results.map { it.asDomainModel() },
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: HttpException) {
            if (e.code() == 402) {
                return LoadResult.Error(HttpLimitExceededException())
            }
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}