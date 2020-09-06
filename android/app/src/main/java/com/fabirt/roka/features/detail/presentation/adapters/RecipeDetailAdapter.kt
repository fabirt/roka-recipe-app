package com.fabirt.roka.features.detail.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.features.detail.domain.model.RecipeDetailItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_SUMMARY = 0
private const val ITEM_VIEW_TYPE_TITLE = 1
private const val ITEM_VIEW_TYPE_INGREDIENT = 2
private const val ITEM_VIEW_TYPE_DIRECTION = 3

class RecipeDetailAdapter :
    ListAdapter<RecipeDetailItem, RecyclerView.ViewHolder>(RecipeDetailItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_SUMMARY -> SectionTitleViewHolder.from(parent)
            ITEM_VIEW_TYPE_TITLE -> SectionTitleViewHolder.from(parent)
            ITEM_VIEW_TYPE_INGREDIENT -> IngredientViewHolder.from(parent)
            ITEM_VIEW_TYPE_DIRECTION -> DirectionViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SectionTitleViewHolder -> {
                val item = getItem(position) as RecipeDetailItem.SectionTitle
                holder.bind(item.text)
            }
            is IngredientViewHolder -> {
                val item = getItem(position) as RecipeDetailItem.RecipeIngredient
                holder.bind(item.element.name)
            }
            is DirectionViewHolder -> {
                val item = getItem(position) as RecipeDetailItem.RecipeDirection
                holder.bind(item.text, item.number)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is RecipeDetailItem.Summary -> ITEM_VIEW_TYPE_SUMMARY
            is RecipeDetailItem.SectionTitle -> ITEM_VIEW_TYPE_TITLE
            is RecipeDetailItem.RecipeIngredient -> ITEM_VIEW_TYPE_INGREDIENT
            is RecipeDetailItem.RecipeDirection -> ITEM_VIEW_TYPE_DIRECTION
        }
    }

    fun submitRecipeInfo(recipe: RecipeInformationModel) {
        adapterScope.launch {
            val items = mutableListOf<RecipeDetailItem>()
            val instructions = recipe.instructions
            if (instructions?.isNotEmpty() == true) {
                val ingredients = mutableListOf<RecipeDetailItem.RecipeIngredient>()
                val directions = mutableListOf<RecipeDetailItem.RecipeDirection>()
                for (step in instructions.first().steps) {
                    val direction = RecipeDetailItem.RecipeDirection(step.number, step.step)
                    directions.add(direction)
                    for (e in step.ingredients) {
                        val ingredient = RecipeDetailItem.RecipeIngredient(e)
                        ingredients.add(ingredient)
                    }
                }
                items.add(RecipeDetailItem.SectionTitle("Ingredients", -100))
                items.addAll(ingredients)
                items.add(RecipeDetailItem.SectionTitle("Directions", -101))
                items.addAll(directions)

                withContext(Dispatchers.Main) {
                    Log.i("RecipeDetailAdapter", items.toString())
                    submitList(items)
                }
            }
        }
    }

    private class SectionTitleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.tvTitle)

        companion object {
            fun from(parent: ViewGroup): SectionTitleViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.text_view_header, parent, false)
                return SectionTitleViewHolder(view)
            }
        }

        fun bind(text: String) {
            textView.text = text
        }
    }

    private class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textView: TextView = view.findViewById(R.id.tvTitle)

        companion object {
            fun from(parent: ViewGroup): IngredientViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.view_ingredient, parent, false)
                return IngredientViewHolder(view)
            }
        }

        fun bind(text: String) {
            textView.text = text
        }
    }

    private class DirectionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.tvTitle)
        private val numberTextView: TextView = view.findViewById(R.id.tvNumber)

        companion object {
            fun from(parent: ViewGroup): DirectionViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val view = inflater.inflate(R.layout.view_direction, parent, false)
                return DirectionViewHolder(view)
            }
        }

        fun bind(title: String, number: Int) {
            titleTextView.text = title
            numberTextView.text = number.toString()
        }
    }

    private class RecipeDetailItemDiffCallback : DiffUtil.ItemCallback<RecipeDetailItem>() {
        override fun areItemsTheSame(
            oldItem: RecipeDetailItem,
            newItem: RecipeDetailItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RecipeDetailItem,
            newItem: RecipeDetailItem
        ): Boolean {
            return oldItem == newItem
        }
    }

}