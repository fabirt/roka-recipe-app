package com.fabirt.roka.features.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.model.InstructionStep
import com.fabirt.roka.core.data.network.model.RecipeElement
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.data.network.model.RecipeInstructions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private val service = RecipesApiClient.getRecipeService()

    private val _recipes = MutableLiveData<List<RecipeInformationModel>>()
    val recipes: LiveData<List<RecipeInformationModel>>
        get() = _recipes

    private val _isSearching = MutableLiveData(true)
    val isSearching: LiveData<Boolean>
        get() = _isSearching

    init {
        requestRecipes()
    }

    fun requestRecipes(query: String = "") {
        viewModelScope.launch {
            try {
                _isSearching.value = true
                val result = service.searchRecipes(query, true)
                _isSearching.value = false
                delay(250)
                _recipes.value = result.results
            } catch (e: Exception) {
                Log.e("SearchViewModel", e.toString())
                _isSearching.value = false
            }
        }
    }

    private fun getFakeData(): List<RecipeInformationModel> {
        val data = RecipeInformationModel(
            1,
            "Foodista",
            "Pasta With Tuna",
            45,
            4,
            "",
            "https://spoonacular.com/recipeImages/654959-312x231.jpg",
            "Summary",
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
            )
        )
        return listOf(data)
    }
}