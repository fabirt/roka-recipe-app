package com.fabirt.roka.core.presentation.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.fabirt.roka.R

class PagingLoadStateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val spinView: View = view.findViewById(R.id.spinView)

    companion object {
        fun create(parent: ViewGroup): PagingLoadStateViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.view_spin_indicator, parent, false)
            return PagingLoadStateViewHolder(view)
        }
    }

    fun bind(loadState: LoadState) {
        spinView.isVisible = loadState is LoadState.Loading
    }
}