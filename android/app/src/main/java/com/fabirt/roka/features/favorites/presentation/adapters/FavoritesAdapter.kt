package com.fabirt.roka.features.favorites.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.presentation.dispatchers.RecipeEventDispatcher
import com.fabirt.roka.core.presentation.viewholders.RecipeViewHolder
import com.fabirt.roka.core.utils.bindNetworkImage

class FavoritesAdapter(
    private val eventDispatcher: RecipeEventDispatcher
) : ListAdapter<Recipe, FavoritesAdapter.FavoritesViewHolder>(RecipeViewHolder.RecipeComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent, eventDispatcher)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe)
    }

    class FavoritesViewHolder(
        view: View,
        private val eventDispatcher: RecipeEventDispatcher
    ) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvName)
        private val image: ImageView = view.findViewById(R.id.ivRecipe)
        private val overlay: View = view.findViewById(R.id.overlayView)

        companion object {
            fun from(
                parent: ViewGroup,
                eventDispatcher: RecipeEventDispatcher
            ): FavoritesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.view_favorite_recipe, parent, false)
                return FavoritesViewHolder(view, eventDispatcher)
            }
        }

        fun bind(recipe: Recipe) {
            name.text = recipe.title
            bindNetworkImage(image, recipe.imageUrl)
            overlay.setOnClickListener {
                eventDispatcher.onRecipePressed(recipe)
            }
        }
    }
}