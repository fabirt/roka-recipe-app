package com.fabirt.roka.core.domain.repository

import com.fabirt.roka.core.data.database.entities.DatabaseRecipeInformation
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

    /**
     * Request the recipe information for the given [id].
     */
    suspend fun requestRecipeInformation(id: Int): Either<Failure, RecipeInformationModel>

    /**
     * Request favorite recipes from the local database.
     */
    suspend fun requestFavoriteRecipes(): List<DatabaseRecipeInformation>
}