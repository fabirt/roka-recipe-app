package com.fabirt.roka.features.categories.presentation.dispatchers

import android.view.View
import com.fabirt.roka.features.categories.domain.model.CategoryItem

interface CategoryEventDispatcher {
    fun onCategoryPressed(category: CategoryItem, view: View)
}