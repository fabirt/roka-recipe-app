package com.fabirt.roka.core.domain.repository

import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.error.Failure
import com.fabirt.roka.core.utils.Either

interface RecipeRepository {
    /**
     * Search recipes by [query].
     */
    suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean
    ): Either<Failure, List<RecipeInformationModel>>
}