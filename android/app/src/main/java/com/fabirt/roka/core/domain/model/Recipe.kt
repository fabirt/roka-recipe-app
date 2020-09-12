package com.fabirt.roka.core.domain.model

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
