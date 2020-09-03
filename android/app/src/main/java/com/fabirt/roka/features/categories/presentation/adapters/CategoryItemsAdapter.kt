package com.fabirt.roka.features.categories.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.features.categories.domain.model.CategoryItem

class CategoryItemsAdapter(
    private val items: List<CategoryItem>,
    private val onItemPressed: (CategoryItem) -> Unit
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
        private val imageView: ImageView = itemView.findViewById(R.id.ivCategoryItem)

        fun bind(item: CategoryItem) {
            tvCategoryName.text = item.name
            bindNetworkImage(imageView, item.imageUrl)
            tvCategoryName.setOnClickListener {
                onItemPressed(item)
            }
        }
    }
}