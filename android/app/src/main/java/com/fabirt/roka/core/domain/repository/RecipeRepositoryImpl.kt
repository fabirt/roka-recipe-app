package com.fabirt.roka.core.domain.repository

import com.fabirt.roka.core.data.network.model.*
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.error.Failure
import com.fabirt.roka.core.utils.Either
import com.fabirt.roka.core.utils.left
import com.fabirt.roka.core.utils.right
import kotlinx.coroutines.delay
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService
) : RecipeRepository {
    override suspend fun searchRecipes(
        query: String,
        addRecipeInformation: Boolean
    ): Either<Failure, List<RecipeInformationModel>> {
        return try {
            //val result = service.searchRecipes(query, addRecipeInformation)
            right(getFakeData())
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    override suspend fun requestRecipeInformation(
        id: Int
    ): Either<Failure, RecipeInformationModel> {
        return try {
            delay(1000)
            right(getFakeData().first())
        } catch (e: Exception) {
            left(Failure.UnexpectedFailure)
        }
    }

    private suspend fun getFakeData(): List<RecipeInformationModel> {
        delay(600)
        val data = RecipeInformationModel(
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
                RecipeInstructions(
                    listOf(
                        InstructionStep(
                            1,
                            "Cook pasta in a large pot of boiling water until al dente.",
                            listOf(
                                RecipeElement(
                                    20420,
                                    "pasta",
                                    ""
                                ),
                                RecipeElement(
                                    14412,
                                    "water",
                                    ""
                                )
                            ),
                            listOf(
                                RecipeElement(
                                    404752,
                                    "pot",
                                    ""
                                )
                            )
                        ),
                        InstructionStep(
                            2,
                            "Drain and return to warm pot. Put olive oil in saucepan and add onion.",
                            listOf(
                                RecipeElement(
                                    4053,
                                    "olive oil",
                                    ""
                                ),
                                RecipeElement(
                                    11282,
                                    "onion",
                                    ""
                                )
                            ),
                            listOf(
                                RecipeElement(
                                    404669,
                                    "sauce pan",
                                    ""
                                ),
                                RecipeElement(
                                    404752,
                                    "pot",
                                    ""
                                )
                            )
                        ),
                        InstructionStep(
                            3,
                            "Saute until transparent. Stir in flour and cook for a few seconds and then whisk in milk. Stir constantly until this thickens.",
                            listOf(
                                RecipeElement(
                                    20081,
                                    "all purpose flour",
                                    ""
                                ),
                                RecipeElement(
                                    1077,
                                    "milk",
                                    ""
                                )
                            ),
                            listOf(
                                RecipeElement(
                                    404661,
                                    "whisk",
                                    ""
                                )
                            )
                        ),
                        InstructionStep(
                            4,
                            "Add peas, tuna (shredded into chunks,) parsley, green onions, cheese and hot pepper sauce.",
                            listOf(
                                RecipeElement(
                                    6168,
                                    "hot sauce",
                                    ""
                                ),
                                RecipeElement(
                                    11291,
                                    "green onions",
                                    ""
                                ),
                                RecipeElement(
                                    11297,
                                    "parsley",
                                    ""
                                ),
                                RecipeElement(
                                    1041009,
                                    "cheese",
                                    ""
                                ),
                                RecipeElement(
                                    11304,
                                    "peas",
                                    ""
                                ),
                                RecipeElement(
                                    10015121,
                                    "tuna",
                                    ""
                                )
                            ),
                            listOf()
                        ),
                        InstructionStep(
                            5,
                            "Pour over pasta and stir gently to mix.",
                            listOf(
                                RecipeElement(
                                    20420,
                                    "pasta",
                                    ""
                                )
                            ),
                            listOf()
                        ),
                        InstructionStep(
                            6,
                            "Serve at once.",
                            listOf(),
                            listOf()
                        )
                    )
                )
            ),
            listOf(
                Ingredient(
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