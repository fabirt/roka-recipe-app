package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.features.categories.domain.model.CategoryItem
import com.fabirt.roka.features.categories.presentation.adapters.CategoriesAdapter
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoriesViewModel
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailViewModel
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.view_spin_indicator.*

class CategoriesFragment : Fragment() {

    private val viewModel: CategoriesViewModel by viewModels()
    private val detailViewModel: CategoryDetailViewModel by activityViewModels()
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter(::navigateToCategoryDetail)
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            spinView.visibility = View.GONE
            rvCategories.scheduleLayoutAnimation()
            adapter.submitList(categories)
        })
    }

    private fun navigateToCategoryDetail(parent: String, category: CategoryItem) {
        detailViewModel.setCurrentCategory(parent, category)
        val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryDetailFragment()
        findNavController().navigate(action)
    }
}