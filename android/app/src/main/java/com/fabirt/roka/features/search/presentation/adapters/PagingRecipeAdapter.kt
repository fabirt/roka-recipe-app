package com.fabirt.roka.features.search.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.databinding.ViewRecipeBinding

class PagingRecipeAdapter(
    private val onRecipePressed: (Recipe) -> Unit
) : PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeComparator) {
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, onRecipePressed) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder.create(parent)
    }
}

class RecipeViewHolder(
    private val binding: ViewRecipeBinding
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun create(parent: ViewGroup): RecipeViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_recipe, parent, false)
            val binding = ViewRecipeBinding.bind(view)
            return RecipeViewHolder(binding)
        }
    }

    fun bind(recipe: Recipe, onPressed: (Recipe) -> Unit) {
        val defaultAuthor = itemView.context.getString(R.string.unknown)
        binding.textName.text = recipe.title
        binding.textAuthor.text = itemView.context.getString(
            R.string.by_source,
            recipe.sourceName ?: defaultAuthor
        )
        binding.textTime.text = itemView.context.getString(
            R.string.minutes_label,
            recipe.readyInMinutes ?: 0
        )
        bindNetworkImage(binding.ivRecipe, recipe.imageUrl)
        binding.cardRecipe.setOnClickListener {
            onPressed(recipe)
        }
    }
}

object RecipeComparator : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }
}