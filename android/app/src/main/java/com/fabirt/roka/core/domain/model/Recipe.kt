package com.fabirt.roka.core.domain.model

import com.fabirt.roka.core.data.database.entities.DatabaseIngredient
import com.fabirt.roka.core.data.database.entities.DatabaseInstruction
import com.fabirt.roka.core.data.database.entities.DatabaseRecipe
import com.fabirt.roka.core.data.database.entities.DatabaseRecipeInformation

data class Recipe(
    val id: Int,
    val title: String,
    val sourceName: String?,
    val sourceUrl: String?,
    val imageUrl: String,
    val readyInMinutes: Int?,
    val servings: Int?,
    val summary: String?,
    val score: Float?,
    val instructions: List<Instruction>?,
    val ingredients: List<Ingredient>?
)

data class Instruction(
    val number: Int,
    val step: String
)

data class Ingredient(
    val id: Int,
    val name: String,
    val original: String,
    val amount: Float,
    val unit: String
)

fun Recipe.toDatabaseModel(): DatabaseRecipeInformation {
    val dbRecipe = DatabaseRecipe(
        id = id,
        title = title,
        createdAt = System.currentTimeMillis(),
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        imageUrl = imageUrl,
        readyInMinutes = readyInMinutes,
        servings = servings,
        summary = summary,
        score = score
    )

    val dbIngredients = ingredients?.map { ingredient ->
        DatabaseIngredient(
            id = ingredient.id,
            recipeId = id,
            name = ingredient.name,
            original = ingredient.original,
            amount = ingredient.amount,
            unit = ingredient.unit
        )
    }

    val dbInstructions = instructions?.map { instruction ->
        DatabaseInstruction(
            recipeId = id,
            number = instruction.number,
            step = instruction.step
        )
    }

    return DatabaseRecipeInformation(
        recipe = dbRecipe,
        ingredients = dbIngredients ?: listOf(),
        instructions = dbInstructions ?: listOf()
    )
}
