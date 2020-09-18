package com.fabirt.roka.features.favorites.domain.model

import com.fabirt.roka.core.domain.model.Recipe

data class FavoriteRecipe(
    val data: Recipe,
    val isSelected: Boolean
) {
    val id: Int get() = data.id
}