package com.fabirt.roka.features.categories.presentation.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.features.categories.domain.model.Category
import com.fabirt.roka.features.categories.domain.model.CategoryItem

class CategoriesAdapter(
    private val onItemPressed: (String, CategoryItem) -> Unit
) : ListAdapter<Category, CategoriesAdapter.CategoriesViewHolder>(CategoryDiffCallback()) {

    private val scrollStates = hashMapOf<String, Parcelable?>()

    override fun onViewRecycled(holder: CategoriesViewHolder) {
        super.onViewRecycled(holder)
        val item = getItem(holder.bindingAdapterPosition)
        val key = item.name
        scrollStates[key] = holder.mlayoutManager.onSaveInstanceState()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoriesViewHolder(
            inflater.inflate(
                R.layout.list_view_home_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        val item = getItem(position)
        val key = item.name
        val state = scrollStates[key]
        holder.bind(item)
        if (state != null) {
            holder.mlayoutManager.onRestoreInstanceState(state)
        } else {
            holder.mlayoutManager.scrollToPosition(0)
        }
    }

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategoryTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)
        private val rvCategoryItems: RecyclerView = itemView.findViewById(R.id.rvCategoryItems)
        val mlayoutManager =
            LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)

        fun bind(category: Category) {
            val adapter = CategoryItemsAdapter(category.type, category.items, onItemPressed)
            adapter.stateRestorationPolicy = StateRestorationPolicy.ALLOW
            tvCategoryTitle.text = category.name
            rvCategoryItems.apply {
                this.adapter = adapter
                this.layoutManager = mlayoutManager
            }
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

}