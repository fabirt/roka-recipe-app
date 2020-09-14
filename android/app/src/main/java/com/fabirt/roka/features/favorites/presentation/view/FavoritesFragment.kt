package com.fabirt.roka.features.favorites.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.core.utils.navigateToRecipeDetail
import com.fabirt.roka.features.favorites.presentation.adapters.FavoritesAdapter
import com.fabirt.roka.features.favorites.presentation.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.view_empty.*

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    companion object {
        private const val TAG = "FavoritesFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = FavoritesAdapter(::navigateToRecipeDetail)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar()
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(requireContext(), 2)
        rvFavorites.layoutManager = layoutManager
        rvFavorites.adapter = adapter
        viewModel.recipes.observe(viewLifecycleOwner, Observer { recipes ->
            if (recipes.isEmpty()) {
                rvFavorites.visibility = View.INVISIBLE
                emptyTextView.text = getString(R.string.no_favorites)
                emptyImageView.setImageResource(R.drawable.ic_favorites)
                emptyView.visibility = View.VISIBLE
            } else {
                rvFavorites.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
                adapter.submitList(recipes)
            }
        })
    }
}