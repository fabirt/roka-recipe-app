package com.fabirt.roka.core.presentation.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.fabirt.roka.core.presentation.viewholders.PagingLoadStateViewHolder

class PagingLoadStateAdapter : LoadStateAdapter<PagingLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: PagingLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateViewHolder {
        return PagingLoadStateViewHolder.create(parent)
    }
}