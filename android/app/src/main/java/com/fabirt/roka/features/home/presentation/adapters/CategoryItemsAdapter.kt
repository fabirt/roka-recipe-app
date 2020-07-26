package com.fabirt.roka.features.home.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R

class CategoryItemsAdapter(
    private val items: List<String>,
    private val onItemPressed: (String) -> Unit
) : RecyclerView.Adapter<CategoryItemsAdapter.CategoryItemsViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CategoryItemsViewHolder(inflater.inflate(R.layout.view_home_category, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryItemsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CategoryItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)

        fun bind(item: String) {
            tvCategoryName.text = item
            tvCategoryName.setOnClickListener {
                onItemPressed(item)
            }
        }
    }
}