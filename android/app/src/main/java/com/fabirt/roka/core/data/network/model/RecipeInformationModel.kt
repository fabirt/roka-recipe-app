package com.fabirt.roka.core.data.network.model

import com.google.gson.annotations.SerializedName

data class RecipeInformationModel(
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
    val instructions: List<RecipeInstructions>?,
    @SerializedName("extendedIngredients")
    val ingredients: List<Ingredient>?
)

data class RecipeInstructions(
    val steps: List<InstructionStep>
)

data class InstructionStep(
    val number: Int,
    val step: String,
    val ingredients: List<RecipeElement>,
    val equipment: List<RecipeElement>
)

data class RecipeElement(
    val id: Int,
    val name: String,
    val image: String
)

data class Ingredient(
    val id: Int,
    val name: String,
    val original: String,
    val amount: Float,
    val unit: String
)

/*
    {
        "vegetarian": false,
        "vegan": false,
        "glutenFree": false,
        "dairyFree": false,
        "veryHealthy": true,
        "cheap": false,
        "veryPopular": false,
        "sustainable": false,
        "weightWatcherSmartPoints": 11,
        "gaps": "no",
        "lowFodmap": false,
        "aggregateLikes": 2,
        "spoonacularScore": 91.0,
        "healthScore": 90.0,
        "creditsText": "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
        "license": "CC BY 3.0",
        "sourceName": "Foodista",
        "pricePerServing": 168.12,
        "id": 654959,
        "title": "Pasta With Tuna",
        "readyInMinutes": 45,
        "servings": 4,
        "sourceUrl": "http://www.foodista.com/recipe/K6QWSKQM/pasta-with-tuna",
        "image": "https://spoonacular.com/recipeImages/654959-312x231.jpg",
        "imageType": "jpg",
        "summary": "Pasta With Tuna might be just the main course you are searching for. One serving contains <b>421 calories</b>, <b>24g of protein</b>, and <b>10g of fat</b>. For <b>$1.68 per serving</b>, this recipe <b>covers 28%</b> of your daily requirements of vitamins and minerals. 1 person were impressed by this recipe. Head to the store and pick up flour, onion, peas, and a few other things to make it today. It is a good option if you're following a <b>pescatarian</b> diet. All things considered, we decided this recipe <b>deserves a spoonacular score of 92%</b>. This score is excellent. Try <a href=\"https://spoonacular.com/recipes/pasta-and-tuna-salad-ensalada-de-pasta-y-atn-226303\">Pastan and Tuna Salad (Ensalada de Pasta y Atún)</a>, <a href=\"https://spoonacular.com/recipes/tuna-pasta-565100\">Tuna Pasta</a>, and <a href=\"https://spoonacular.com/recipes/tuna-pasta-89136\">Tuna Pasta</a> for similar recipes.",
        "cuisines": [],
        "dishTypes": [
            "lunch",
            "main course",
            "main dish",
            "dinner"
        ],
        "diets": [
            "pescatarian"
        ],
        "occasions": [],
        "extendedIngredients": [
            {
                "id": 20081,
                "aisle": "Baking",
                "image": "flour.png",
                "consistency": "solid",
                "name": "flour",
                "original": "2 tablespoons Flour",
                "originalString": "2 tablespoons Flour",
                "originalName": "Flour",
                "amount": 2.0,
                "unit": "tablespoons",
                "meta": [],
                "metaInformation": [],
                "measures": {
                    "us": {
                        "amount": 2.0,
                        "unitShort": "Tbsps",
                        "unitLong": "Tbsps"
                    },
                    "metric": {
                        "amount": 2.0,
                        "unitShort": "Tbsps",
                        "unitLong": "Tbsps"
                    }
                }
            }
        ],
        "analyzedInstructions": [
            {
                "name": "",
                "steps": [
                    {
                        "number": 1,
                        "step": "Cook pasta in a large pot of boiling water until al dente.",
                        "ingredients": [
                            {
                                "id": 20420,
                                "name": "pasta",
                                "localizedName": "pasta",
                                "image": "fusilli.jpg"
                            },
                            {
                                "id": 14412,
                                "name": "water",
                                "localizedName": "water",
                                "image": "water.png"
                            }
                        ],
                        "equipment": [
                            {
                                "id": 404752,
                                "name": "pot",
                                "localizedName": "pot",
                                "image": "stock-pot.jpg"
                            }
                        ]
                    },
                    {
                        "number": 2,
                        "step": "Drain and return to warm pot. Put olive oil in saucepan and add onion.",
                        "ingredients": [
                            {
                                "id": 4053,
                                "name": "olive oil",
                                "localizedName": "olive oil",
                                "image": "olive-oil.jpg"
                            },
                            {
                                "id": 11282,
                                "name": "onion",
                                "localizedName": "onion",
                                "image": "brown-onion.png"
                            }
                        ],
                        "equipment": [
                            {
                                "id": 404669,
                                "name": "sauce pan",
                                "localizedName": "sauce pan",
                                "image": "sauce-pan.jpg"
                            },
                            {
                                "id": 404752,
                                "name": "pot",
                                "localizedName": "pot",
                                "image": "stock-pot.jpg"
                            }
                        ]
                    },
                    {
                        "number": 3,
                        "step": "Saute until transparent. Stir in flour and cook for a few seconds and then whisk in milk. Stir constantly until this thickens.",
                        "ingredients": [
                            {
                                "id": 20081,
                                "name": "all purpose flour",
                                "localizedName": "all purpose flour",
                                "image": "flour.png"
                            },
                            {
                                "id": 1077,
                                "name": "milk",
                                "localizedName": "milk",
                                "image": "milk.png"
                            }
                        ],
                        "equipment": [
                            {
                                "id": 404661,
                                "name": "whisk",
                                "localizedName": "whisk",
                                "image": "whisk.png"
                            }
                        ]
                    },
                    {
                        "number": 4,
                        "step": "Add peas, tuna (shredded into chunks,) parsley, green onions, cheese and hot pepper sauce.",
                        "ingredients": [
                            {
                                "id": 6168,
                                "name": "hot sauce",
                                "localizedName": "hot sauce",
                                "image": "hot-sauce-or-tabasco.png"
                            },
                            {
                                "id": 11291,
                                "name": "green onions",
                                "localizedName": "green onions",
                                "image": "spring-onions.jpg"
                            },
                            {
                                "id": 11297,
                                "name": "parsley",
                                "localizedName": "parsley",
                                "image": "parsley.jpg"
                            },
                            {
                                "id": 1041009,
                                "name": "cheese",
                                "localizedName": "cheese",
                                "image": "cheddar-cheese.png"
                            },
                            {
                                "id": 11304,
                                "name": "peas",
                                "localizedName": "peas",
                                "image": "peas.jpg"
                            },
                            {
                                "id": 10015121,
                                "name": "tuna",
                                "localizedName": "tuna",
                                "image": "canned-tuna.png"
                            }
                        ],
                        "equipment": []
                    },
                    {
                        "number": 5,
                        "step": "Pour over pasta and stir gently to mix.",
                        "ingredients": [
                            {
                                "id": 20420,
                                "name": "pasta",
                                "localizedName": "pasta",
                                "image": "fusilli.jpg"
                            }
                        ],
                        "equipment": []
                    },
                    {
                        "number": 6,
                        "step": "Serve at once.",
                        "ingredients": [],
                        "equipment": []
                    }
                ]
            }
        ],
        "spoonacularSourceUrl": "https://spoonacular.com/pasta-with-tuna-654959"
    }

*/