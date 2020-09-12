package com.fabirt.roka.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.fabirt.roka.core.data.database.entities.DatabaseRecipeInformation

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes")
    suspend fun getRecipesWithInformation(): List<DatabaseRecipeInformation>
}