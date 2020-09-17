package com.fabirt.roka.features.favorites.presentation.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.databinding.ViewFavoriteRecipeBinding
import com.fabirt.roka.features.favorites.presentation.dispatchers.FavoriteRecipeEventDispatcher

class FavoriteRecipeViewHolder(
    private val binding: ViewFavoriteRecipeBinding,
    private val eventDispatcher: FavoriteRecipeEventDispatcher
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(
            parent: ViewGroup,
            eventDispatcher: FavoriteRecipeEventDispatcher
        ): FavoriteRecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ViewFavoriteRecipeBinding.inflate(inflater, parent, false)
            return FavoriteRecipeViewHolder(binding, eventDispatcher)
        }
    }

    fun bind(recipe: Recipe) {
        binding.cardView.transitionName = recipe.id.toString()
        binding.tvName.text = recipe.title
        bindNetworkImage(binding.ivRecipe, recipe.imageUrl)
        binding.overlayView.setOnClickListener {
            eventDispatcher.onFavoriteRecipePressed(recipe, binding.cardView)
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