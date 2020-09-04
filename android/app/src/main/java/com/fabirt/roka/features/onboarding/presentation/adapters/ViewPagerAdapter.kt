package com.fabirt.roka.features.onboarding.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R
import com.fabirt.roka.features.onboarding.domain.model.OnboardingItem

class ViewPagerAdapter(
    var items: List<OnboardingItem>
) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewPagerViewHolder(
            inflater.inflate(
                R.layout.view_onboarding_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    inner class ViewPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.obTitle)
        private val body: TextView = itemView.findViewById(R.id.obBody)
        private val image: ImageView = itemView.findViewById(R.id.obImage)

        fun bind(item: OnboardingItem) {
            title.text = itemView.context.getString(item.titleResId)
            body.text = itemView.context.getString(item.bodyResId)
            image.setImageDrawable(ContextCompat.getDrawable(itemView.context, item.imageResId))
        }
    }
}