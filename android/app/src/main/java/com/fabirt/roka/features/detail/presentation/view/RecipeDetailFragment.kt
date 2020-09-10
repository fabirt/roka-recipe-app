package com.fabirt.roka.features.detail.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.data.network.model.RecipeInformationModel
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.features.detail.presentation.adapters.RecipeDetailAdapter
import com.fabirt.roka.features.detail.presentation.view_model.DetailViewModel
import kotlinx.android.synthetic.main.fragment_recipe_detail.*

class RecipeDetailFragment : Fragment() {

    private val viewModel: DetailViewModel by activityViewModels()
    private lateinit var adapter: RecipeDetailAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecipeDetailAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        rvDetails.adapter = adapter
        rvDetails.layoutManager = layoutManager
        viewModel.recipeInfo.observe(viewLifecycleOwner, Observer(::buildView))

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        ivRecipe.setOnClickListener {
            openPhotoFragment()
        }

        btnBack.setOnApplyWindowInsetsListener { v, insets ->
            v.updatePadding(top = insets.systemWindowInsetTop + 18)
            insets
        }
    }

    private fun buildView(recipe: RecipeInformationModel) {
        rvDetails.scheduleLayoutAnimation()
        adapter.submitRecipeInfo(recipe)
        bindNetworkImage(ivRecipe, recipe.imageUrl)
        tvName.text = recipe.title
        tvPeople.text = recipe.servings?.toString()
        tvTime.text = getString(R.string.minutes_label, recipe.readyInMinutes)
        tvScore.text = recipe.score?.toString()
    }

    private fun openPhotoFragment() {
        viewModel.recipeInfo.value?.let { recipe ->
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToPhotoRecipeFragment(recipe.imageUrl)
            val extras = FragmentNavigatorExtras(
                ivRecipe to PhotoRecipeFragment.SHARED_IMAGE
            )
            findNavController().navigate(action, extras)
        }
    }
}