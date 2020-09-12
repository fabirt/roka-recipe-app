package com.fabirt.roka.features.favorites.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.bindNetworkImage

class FavoritesAdapter(
    private val onRecipePressed: (Recipe) -> Unit
) : ListAdapter<Recipe, FavoritesAdapter.FavoritesViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.bind(recipe, onRecipePressed)
    }

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvName)
        private val image: ImageView = view.findViewById(R.id.ivRecipe)
        private val overlay: View = view.findViewById(R.id.overlayView)

        companion object {
            fun from(parent: ViewGroup): FavoritesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.view_favorite_recipe, parent, false)
                return FavoritesViewHolder(view)
            }
        }

        fun bind(recipe: Recipe, onRecipePressed: (Recipe) -> Unit) {
            name.text = recipe.title
            bindNetworkImage(image, recipe.imageUrl)
            overlay.setOnClickListener {
                onRecipePressed(recipe)
            }
        }
    }

    private class FavoritesDiffCallback : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
}