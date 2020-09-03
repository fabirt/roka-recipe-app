package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.features.categories.constants.categories
import com.fabirt.roka.features.categories.presentation.adapters.CategoriesAdapter
import kotlinx.android.synthetic.main.fragment_categories.*

class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CategoriesAdapter(categories) { item ->
            Toast.makeText(requireContext(), item.name, Toast.LENGTH_SHORT).show()
        }
        rvCategories.layoutManager = LinearLayoutManager(requireContext())
        rvCategories.adapter = adapter
    }
}