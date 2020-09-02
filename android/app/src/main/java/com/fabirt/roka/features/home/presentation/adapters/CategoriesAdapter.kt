package com.fabirt.roka.features.home.presentation.adapters

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.features.home.domain.entities.Category
import com.fabirt.roka.features.home.domain.entities.CategoryItem

class CategoriesAdapter(
    private val categories: List<Category>,
    private val onItemPressed: (CategoryItem) -> Unit
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val scrollStates = hashMapOf<String, Parcelable?>()

    override fun getItemCount(): Int = categories.size

    override fun onViewRecycled(holder: CategoriesViewHolder) {
        super.onViewRecycled(holder)
        val key = categories[holder.bindingAdapterPosition].name
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
        val key = categories[holder.bindingAdapterPosition].name
        val state = scrollStates[key]
        holder.bind(categories[position])
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
            val adapter = CategoryItemsAdapter(category.items, onItemPressed)
            adapter.stateRestorationPolicy = StateRestorationPolicy.ALLOW
            tvCategoryTitle.text = category.name
            rvCategoryItems.apply {
                this.adapter = adapter
                this.layoutManager = mlayoutManager
            }
        }
    }

}