package com.fabirt.roka.core.domain.repository

import com.fabirt.roka.core.data.database.dao.RecipeDao
import com.fabirt.roka.core.data.database.entities.asDomainModel
import com.fabirt.roka.core.data.network.model.*
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.domain.model.Ingredient
import com.fabirt.roka.core.domain.model.Instruction
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.domain.model.toDatabaseModel
import com.fabirt.roka.core.error.Failure
import com.fabirt.roka.core.utils.Either
import com.fabirt.roka.core.utils.left
import com.fabirt.roka.core.utils.right
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService,
    private val recipeDao: RecipeDao
) : RecipeRepository {
    override suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean
    ): Either<Failure, List<Recipe>> {
        return try {
            return right(getFakeData())
            val result = service.searchRecipes(query, addRecipeInformation)
            val recipes = result.results.map { it.asDomainModel() }
            right(recipes)
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun requestRecipeInformation(
        id: Int
    ): Either<Failure, Recipe> {
        return try {
            return right(getFakeData().first())
            val response = service.requestRecipeInformation(id)
            right(response.asDomainModel())
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override fun requestFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipesWithInformation().map { dbRecipe ->
            dbRecipe.map { it.asDomainModel() }
        }
    }

    override suspend fun saveFavoriteRecipe(recipe: Recipe) {
        val model = recipe.toDatabaseModel()
        recipeDao.insertRecipe(
            recipe = model.recipe,
            ingredients = model.ingredients,
            instructions = model.instructions
        )
    }

    override fun requestFavoriteRecipeById(id: Int): Flow<Recipe?> {
        return recipeDao.getRecipeById(id).map { it?.asDomainModel() }
    }

    private suspend fun getFakeData(): List<Recipe> {
        delay(600)
        val data = Recipe(
            id = 1,
            sourceName = "Foodista",
            title = "Pasta With Tuna",
            readyInMinutes = 45,
            servings = 4,
            sourceUrl = "http://www.foodista.com/recipe/K6QWSKQM/pasta-with-tuna",
            imageUrl = "https://spoonacular.com/recipeImages/654959-312x231.jpg",
            summary = "Summary",
            score = 91f,
            ingredients = listOf(
                Ingredient(
                    id = 20081,
                    name = "flour",
                    original = "2 tablespoons Flour",
                    amount = 2.0f,
                    unit = "tablespoons"
                )
            ),
            instructions = listOf(
                Instruction(
                    1,
                    "Cook pasta in a large pot of boiling water until al dente."
                ),
                Instruction(
                    2,
                    "Drain and return to warm pot. Put olive oil in saucepan and add onion."
                ),
                Instruction(
                    3,
                    "Saute until transparent. Stir in flour and cook for a few seconds and then whisk in milk. Stir constantly until this thickens."
                ),
                Instruction(
                    4,
                    "Add peas, tuna (shredded into chunks,) parsley, green onions, cheese and hot pepper sauce."
                ),
                Instruction(
                    5,
                    "Pour over pasta and stir gently to mix."
                ),
                Instruction(
                    6,
                    "Serve at once."
                )
            )
        )
        return listOf(data, data.copy(id = 2), data.copy(id = 3), data.copy(id = 4))
    }
}