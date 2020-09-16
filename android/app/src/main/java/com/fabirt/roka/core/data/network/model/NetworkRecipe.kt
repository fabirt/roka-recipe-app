package com.fabirt.roka.core.data.network.model

import com.fabirt.roka.core.domain.model.Ingredient
import com.fabirt.roka.core.domain.model.Instruction
import com.fabirt.roka.core.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class NetworkRecipe(
    val id: Int,
    val sourceName: String?,
    val title: String,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceUrl: String?,
    @SerializedName("image")
    val imageUrl: String,
    val summary: String?,
    @SerializedName("spoonacularScore")
    val score: Float?,
    @SerializedName("analyzedInstructions")
    val instructions: List<NetworkInstructions>?,
    @SerializedName("extendedIngredients")
    val ingredients: List<NetworkIngredient>?
)

data class NetworkInstructions(
    val steps: List<NetworkStep>
)

data class NetworkStep(
    val number: Int,
    val step: String,
    val ingredients: List<NetworkRecipeElement>,
    val equipment: List<NetworkRecipeElement>
)

data class NetworkRecipeElement(
    val id: Int,
    val name: String,
    val image: String
)

data class NetworkIngredient(
    val id: Int,
    val name: String,
    val original: String,
    val amount: Float,
    val unit: String
)

// Extensions

fun NetworkRecipe.asDomainModel(): Recipe {
    var instructions = listOf<Instruction>()
    var ingredients = listOf<Ingredient>()

    if (this.instructions?.isNotEmpty() == true) {
        instructions = this.instructions.first().steps.map {
            it.asDomainModel()
        }
    }

    if (this.ingredients?.isNotEmpty() == true) {
        ingredients = this.ingredients.map {
            it.asDomainModel()
        }
    }

    return Recipe(
        id = id,
        title = title,
        sourceName = sourceName,
        sourceUrl = sourceUrl,
        imageUrl = imageUrl,
        readyInMinutes = readyInMinutes,
        servings = servings,
        summary = summary,
        score = score,
        instructions = instructions,
        ingredients = ingredients
    )
}

fun NetworkStep.asDomainModel() = Instruction(number, step)

fun NetworkIngredient.asDomainModel() = Ingredient(id, name, original, amount, unit)
