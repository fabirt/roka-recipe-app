package com.fabirt.roka.features.search.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.data.network.model.NetworkRecipe
import com.fabirt.roka.core.utils.bindNetworkImage

class RecipeAdapter(
    private var recipes: List<NetworkRecipe>,
    private val onRecipePressed: (NetworkRecipe, ImageView) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipes.count()

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    fun submitList(data: List<NetworkRecipe>) {
        recipes = data
        notifyDataSetChanged()
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.ivRecipe)
        private val title: TextView = itemView.findViewById(R.id.textName)
        private val author: TextView = itemView.findViewById(R.id.textAuthor)
        private val time: TextView = itemView.findViewById(R.id.textTime)

        fun bind(recipe: NetworkRecipe) {
            val defaultAuthor = itemView.context.getString(R.string.unknown)
            title.text = recipe.title
            author.text =
                itemView.context.getString(R.string.by_source, recipe.sourceName ?: defaultAuthor)
            time.text =
                itemView.context.getString(R.string.minutes_label, recipe.readyInMinutes ?: 0)
            bindNetworkImage(image, recipe.imageUrl)
            itemView.setOnClickListener {
                onRecipePressed(recipe, image)
            }
        }
    }
}