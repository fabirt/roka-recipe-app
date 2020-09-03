package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.features.categories.presentation.adapters.CategoriesAdapter
import com.fabirt.roka.features.categories.presentation.view_model.CategoriesViewModel
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    private val viewModel: CategoriesViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter(listOf()) { item ->
            Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
        }
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        rvCategories.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
            adapter.categories = categories
            adapter.notifyDataSetChanged()
        })
    }
}