package com.fabirt.roka.features.detail.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.features.detail.domain.model.RecipeDetailItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.ClassCastException

class RecipeDetailAdapter :
    ListAdapter<RecipeDetailItem, RecyclerView.ViewHolder>(RecipeDetailItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    companion object {
        private const val TAG = "RecipeDetailAdapter"
        private const val ITEM_VIEW_TYPE_SUMMARY = 0
        private const val ITEM_VIEW_TYPE_TITLE = 1
        private const val ITEM_VIEW_TYPE_INGREDIENT = 2
        private const val ITEM_VIEW_TYPE_DIRECTION = 3
    }

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
                holder.bind(item.ingredient.original)
            }
            is DirectionViewHolder -> {
                val item = getItem(position) as RecipeDetailItem.RecipeDirection
                holder.bind(item.instruction.step, item.instruction.number)
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

    fun submitRecipeInfo(context: Context, recipe: Recipe) {
        val items = mutableListOf<RecipeDetailItem>()

        if (recipe.ingredients?.isNotEmpty() == true) {
            val ingredientsTitle = context.getString(R.string.ingredients_title)
            val ingredients = recipe.ingredients.map { ingredient ->
                RecipeDetailItem.RecipeIngredient(ingredient)
            }
            items.add(RecipeDetailItem.SectionTitle(ingredientsTitle, -100))
            items.addAll(ingredients)
        }

        if (recipe.instructions?.isNotEmpty() == true) {
            val preparationTitle = context.getString(R.string.preparation_title)
            val directions = recipe.instructions.map { instruction ->
                RecipeDetailItem.RecipeDirection(instruction)
            }
            items.add(RecipeDetailItem.SectionTitle(preparationTitle, -101))
            items.addAll(directions)
        }

        submitList(items)

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
        ) = oldItem.id == newItem.id && oldItem::class == newItem::class

        override fun areContentsTheSame(
            oldItem: RecipeDetailItem,
            newItem: RecipeDetailItem
        ) = oldItem == newItem
    }
}