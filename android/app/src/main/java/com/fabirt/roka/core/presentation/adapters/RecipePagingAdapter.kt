package com.fabirt.roka.core.presentation.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.presentation.dispatchers.RecipeEventDispatcher
import com.fabirt.roka.core.presentation.viewholders.RecipeViewHolder

class RecipePagingAdapter(
    private val eventDispatcher: RecipeEventDispatcher
) : PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeViewHolder.RecipeComparator) {
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.create(parent, eventDispatcher)
    }
}