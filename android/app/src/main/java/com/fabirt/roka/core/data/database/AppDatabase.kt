package com.fabirt.roka.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fabirt.roka.core.data.database.dao.RecipeDao
import com.fabirt.roka.core.data.database.entities.DatabaseIngredient
import com.fabirt.roka.core.data.database.entities.DatabaseInstruction
import com.fabirt.roka.core.data.database.entities.DatabaseRecipe

@Database(
    version = 1,
    exportSchema = false,
    entities = [DatabaseRecipe::class, DatabaseIngredient::class, DatabaseInstruction::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}