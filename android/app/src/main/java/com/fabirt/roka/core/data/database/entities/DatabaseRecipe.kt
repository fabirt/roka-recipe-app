package com.fabirt.roka.core.data.database.entities

import androidx.room.*

@Entity(tableName = "recipes")
data class DatabaseRecipe(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "source_name")
    val sourceName: String?,
    val title: String,
    @ColumnInfo(name = "ready_in_minutes")
    val readyInMinutes: Int?,
    val servings: Int?,
    @ColumnInfo(name = "source_url")
    val sourceUrl: String?,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val summary: String?,
    val score: Float?
)

data class DatabaseRecipeInformation(
    @Embedded val recipe: DatabaseRecipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val instructions: List<DatabaseInstruction>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipe_id"
    )
    val ingredients: List<DatabaseIngredient>
)
