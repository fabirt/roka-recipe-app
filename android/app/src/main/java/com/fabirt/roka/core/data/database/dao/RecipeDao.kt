package com.fabirt.roka.core.data.database.dao

import androidx.room.*
import com.fabirt.roka.core.data.database.entities.DatabaseIngredient
import com.fabirt.roka.core.data.database.entities.DatabaseInstruction
import com.fabirt.roka.core.data.database.entities.DatabaseRecipe
import com.fabirt.roka.core.data.database.entities.DatabaseRecipeInformation

@Dao
interface RecipeDao {

    @Transaction
    @Query("SELECT * FROM recipes")
    suspend fun getRecipesWithInformation(): List<DatabaseRecipeInformation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(
        recipe: DatabaseRecipe,
        ingredients: List<DatabaseIngredient>,
        instructions: List<DatabaseInstruction>
    )
}