package com.fabirt.roka.features.favorites.presentation.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.features.favorites.presentation.dispatchers.FavoriteRecipeEventDispatcher

class FavoriteRecipeViewHolder(
    view: View,
    private val eventDispatcher: FavoriteRecipeEventDispatcher
) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.tvName)
    private val image: ImageView = view.findViewById(R.id.ivRecipe)
    private val overlay: View = view.findViewById(R.id.overlayView)

    companion object {
        fun from(
            parent: ViewGroup,
            eventDispatcher: FavoriteRecipeEventDispatcher
        ): FavoriteRecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_favorite_recipe, parent, false)
            return FavoriteRecipeViewHolder(view, eventDispatcher)
        }
    }

    fun bind(recipe: Recipe) {
        name.text = recipe.title
        bindNetworkImage(image, recipe.imageUrl)
        overlay.setOnClickListener {
            eventDispatcher.onFavoriteRecipePressed(recipe)
        }
    }

    object FavoriteRecipeComparator : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}