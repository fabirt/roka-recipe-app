package com.fabirt.roka.features.search.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.core.utils.bindNetworkImage

class PagingRecipeAdapter(
    private val onRecipePressed: (Recipe) -> Unit
) : PagingDataAdapter<Recipe, RecipeViewHolder>(RecipeComparator) {
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it, onRecipePressed) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_recipe, parent, false)
        return RecipeViewHolder(view)
    }
}

class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val container: View = itemView.findViewById(R.id.cardRecipe)
    private val image: ImageView = itemView.findViewById(R.id.ivRecipe)
    private val title: TextView = itemView.findViewById(R.id.textName)
    private val author: TextView = itemView.findViewById(R.id.textAuthor)
    private val time: TextView = itemView.findViewById(R.id.textTime)

    fun bind(recipe: Recipe, onPressed: (Recipe) -> Unit) {
        val defaultAuthor = itemView.context.getString(R.string.unknown)
        title.text = recipe.title
        author.text =
            itemView.context.getString(R.string.by_source, recipe.sourceName ?: defaultAuthor)
        time.text =
            itemView.context.getString(R.string.minutes_label, recipe.readyInMinutes ?: 0)
        bindNetworkImage(image, recipe.imageUrl)
        container.setOnClickListener {
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