package com.fabirt.roka.core.data.network.services

import com.fabirt.roka.core.constants.K
import com.fabirt.roka.core.data.network.model.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {
    @GET(K.COMPLEX_SEARCH_PATH)
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean
    ): RecipeSearchResponse
}