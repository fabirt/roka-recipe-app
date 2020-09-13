package com.fabirt.roka.features.categories.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.fabirt.roka.R
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailState
import com.fabirt.roka.features.categories.presentation.viewmodel.CategoryDetailViewModel
import kotlinx.android.synthetic.main.fragment_category_detail.*

class CategoryDetailFragment : Fragment() {
    private val viewModel: CategoryDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.category.observe(viewLifecycleOwner, Observer { category ->
            tvTitle.text = category.name
        })

        viewModel.state.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                CategoryDetailState.Loading -> {

                }
                is CategoryDetailState.Success -> {

                }
                is CategoryDetailState.Error -> {

                }
            }
        })
    }
}