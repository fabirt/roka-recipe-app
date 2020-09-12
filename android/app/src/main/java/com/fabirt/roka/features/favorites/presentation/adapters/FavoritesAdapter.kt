package com.fabirt.roka.features.favorites.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe

class FavoritesAdapter :
    ListAdapter<Recipe, FavoritesAdapter.FavoritesViewHolder>(FavoritesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        return FavoritesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        // TODO
    }

    class FavoritesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): FavoritesViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.view_favorite_recipe, parent, false)
                return FavoritesViewHolder(view)
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