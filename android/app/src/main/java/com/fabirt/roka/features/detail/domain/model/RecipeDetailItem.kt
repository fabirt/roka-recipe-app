package com.fabirt.roka.features.detail.domain.model

import com.fabirt.roka.core.data.network.model.RecipeElement

sealed class RecipeDetailItem {
    abstract val id: Int

    data class Summary(val text: String, override val id: Int) : RecipeDetailItem()

    data class SectionTitle(val text: String, override val id: Int) : RecipeDetailItem()

    data class RecipeIngredient(val element: RecipeElement) : RecipeDetailItem() {
        override val id: Int
            get() = element.id
    }

    data class RecipeDirection(val number: Int, val text: String) : RecipeDetailItem() {
        override val id: Int
            get() = number + text.hashCode()
    }
}