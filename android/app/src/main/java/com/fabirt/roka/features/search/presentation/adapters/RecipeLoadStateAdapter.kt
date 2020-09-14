package com.fabirt.roka.features.search.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R

class RecipeLoadStateAdapter : LoadStateAdapter<RecipeLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: RecipeLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): RecipeLoadStateViewHolder {
        return RecipeLoadStateViewHolder.create(parent)
    }
}

class RecipeLoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val spinView: View = view.findViewById(R.id.spinView)

    companion object {
        fun create(parent: ViewGroup): RecipeLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_spin_indicator, parent, false)
            return RecipeLoadStateViewHolder(view)
        }
    }

    fun bind(loadState: LoadState) {
        spinView.isVisible = loadState is LoadState.Loading
    }
}