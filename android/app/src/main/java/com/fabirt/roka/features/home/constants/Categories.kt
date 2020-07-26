package com.fabirt.roka.features.home.constants

import com.fabirt.roka.features.home.domain.entities.Category

val categories: List<Category> = listOf(
    Category(
        "Cuisines",
        listOf(
            "African",
            "American",
            "British",
            "Caribbean",
            "Chinese",
            "European",
            "French",
            "German",
            "Greek",
            "Indian",
            "Irish",
            "Italian",
            "Japanese",
            "Jewish",
            "Korean",
            "Latin American",
            "Mexican",
            "Nordic",
            "Spanish",
            "Thai",
            "Vietnamese"
        )
    ),
    Category(
        "Diets",
        listOf(
            "Gluten Free",
            "Ketogenic",
            "Vegetarian",
            "Lacto-Vegatarian",
            "Ovo-Vegetarian",
            "Vegan",
            "Pescetarian",
            "Paleo",
            "Primal"
        )
    ),
    Category(
        "Meal Types",
        listOf(
            "Main Course",
            "Side Dish",
            "Dessert",
            "Appetizer",
            "Salad",
            "Bread",
            "Breakfast",
            "Soup",
            "Beverage",
            "Sauce",
            "Marinade",
            "Fingerfood",
            "Snack",
            "Drink"
        )
    ),
    Category(
        "Intolerances",
        listOf(
            "Dairy",
            "Egg",
            "Gluten",
            "Grain",
            "Peanut",
            "Seafood",
            "Sesame",
            "Shellfish",
            "Soy",
            "Sulfite",
            "Tree Nut",
            "Wheat"
        )
    )
)