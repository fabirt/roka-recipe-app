package com.fabirt.roka.core.domain.repository

import com.fabirt.roka.core.data.database.dao.RecipeDao
import com.fabirt.roka.core.data.database.entities.DatabaseRecipeInformation
import com.fabirt.roka.core.data.network.model.*
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.error.Failure
import com.fabirt.roka.core.utils.Either
import com.fabirt.roka.core.utils.left
import com.fabirt.roka.core.utils.right
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService,
    private val recipeDao: RecipeDao
) : RecipeRepository {
    override suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean
    ): Either<Failure, List<NetworkRecipe>> {
        return try {
            val result = service.searchRecipes(query, addRecipeInformation)
            right(result.results)
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun requestRecipeInformation(
        id: Int
    ): Either<Failure, NetworkRecipe> {
        return try {
            val response = service.requestRecipeInformation(id)
            right(response)
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun requestFavoriteRecipes(): List<DatabaseRecipeInformation> {
        return recipeDao.getRecipesWithInformation()
    }

    private suspend fun getFakeData(): List<NetworkRecipe> {
        delay(600)
        val data = NetworkRecipe(
            1,
            "Foodista",
            "Pasta With Tuna",
            45,
            4,
            "http://www.foodista.com/recipe/K6QWSKQM/pasta-with-tuna",
            "https://spoonacular.com/recipeImages/654959-312x231.jpg",
            "Summary",
            91f,
            listOf(
                NetworkInstructions(
                    listOf(
                        NetworkStep(
                            1,
                            "Cook pasta in a large pot of boiling water until al dente.",
                            listOf(
                                NetworkRecipeElement(
                                    20420,
                                    "pasta",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    14412,
                                    "water",
                                    ""
                                )
                            ),
                            listOf(
                                NetworkRecipeElement(
                                    404752,
                                    "pot",
                                    ""
                                )
                            )
                        ),
                        NetworkStep(
                            2,
                            "Drain and return to warm pot. Put olive oil in saucepan and add onion.",
                            listOf(
                                NetworkRecipeElement(
                                    4053,
                                    "olive oil",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    11282,
                                    "onion",
                                    ""
                                )
                            ),
                            listOf(
                                NetworkRecipeElement(
                                    404669,
                                    "sauce pan",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    404752,
                                    "pot",
                                    ""
                                )
                            )
                        ),
                        NetworkStep(
                            3,
                            "Saute until transparent. Stir in flour and cook for a few seconds and then whisk in milk. Stir constantly until this thickens.",
                            listOf(
                                NetworkRecipeElement(
                                    20081,
                                    "all purpose flour",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    1077,
                                    "milk",
                                    ""
                                )
                            ),
                            listOf(
                                NetworkRecipeElement(
                                    404661,
                                    "whisk",
                                    ""
                                )
                            )
                        ),
                        NetworkStep(
                            4,
                            "Add peas, tuna (shredded into chunks,) parsley, green onions, cheese and hot pepper sauce.",
                            listOf(
                                NetworkRecipeElement(
                                    6168,
                                    "hot sauce",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    11291,
                                    "green onions",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    11297,
                                    "parsley",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    1041009,
                                    "cheese",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    11304,
                                    "peas",
                                    ""
                                ),
                                NetworkRecipeElement(
                                    10015121,
                                    "tuna",
                                    ""
                                )
                            ),
                            listOf()
                        ),
                        NetworkStep(
                            5,
                            "Pour over pasta and stir gently to mix.",
                            listOf(
                                NetworkRecipeElement(
                                    20420,
                                    "pasta",
                                    ""
                                )
                            ),
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
            ),
            listOf(
                NetworkIngredient(
                    20081,
                    "flour",
                    "2 tablespoons Flour",
                    2.0f,
                    "tablespoons"
                )
            )
        )
        return listOf(data, data.copy(id = 2), data.copy(id = 3), data.copy(id = 4))
    }
}