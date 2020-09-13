package com.fabirt.roka.features.categories.domain.model

data class Category(
    val type: String,
    val name: String,
    val items: List<CategoryItem>
)