package com.fabirt.roka.core.data.network.services

import com.fabirt.roka.core.data.network.model.*
import kotlinx.coroutines.delay

class RecipeServiceFakeImpl : RecipeService {
    override suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int
    ): RecipeSearchResponse {
        return RecipeSearchResponse(
            results = getFakeData(),
            number = 1,
            offset = 0,
            totalResults = 1
        )
    }

    override suspend fun searchRecipes(
        addRecipeInformation: Boolean,
        number: Int,
        offset: Int,
        options: Map<String, String>
    ): RecipeSearchResponse {
        return RecipeSearchResponse(
            results = getFakeData(),
            number = 1,
            offset = 0,
            totalResults = 1
        )
    }

    override suspend fun requestRecipeInformation(id: Int): NetworkRecipe {
        return getFakeData().first()
    }

    private suspend fun getFakeData(): List<NetworkRecipe> {
        delay(2000)
        val data = NetworkRecipe(
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
                NetworkIngredient(
                    id = 20081,
                    name = "flour",
                    original = "2 tablespoons Flour",
                    amount = 2.0f,
                    unit = "tablespoons"
                )
            ),
            instructions = listOf(
                NetworkInstructions(
                    listOf(
                        NetworkStep(
                            1,
                            "Cook pasta in a large pot of boiling water until al dente.",
                            listOf(),
                            listOf()
                        ),
                        NetworkStep(
                            2,
                            "Drain and return to warm pot. Put olive oil in saucepan and add onion.",
                            listOf(),
                            listOf()
                        ),
                        NetworkStep(
                            3,
                            "Saute until transparent. Stir in flour and cook for a few seconds and then whisk in milk. Stir constantly until this thickens.",
                            listOf(),
                            listOf()
                        ),
                        NetworkStep(
                            4,
                            "Add peas, tuna (shredded into chunks,) parsley, green onions, cheese and hot pepper sauce.",
                            listOf(),
                            listOf()
                        ),
                        NetworkStep(
                            5,
                            "Pour over pasta and stir gently to mix.",
                            listOf(),
                            listOf()
                        ),
                        NetworkStep(
                            6,
                            "Serve at once.",
                            listOf(),
                            listOf()
                        )
                    )
                )
            )
        )
        return listOf(data, data.copy(id = 2), data.copy(id = 3), data.copy(id = 4))
    }
}