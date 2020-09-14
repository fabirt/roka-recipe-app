package com.fabirt.roka.core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fabirt.roka.MainGraphDirections
import com.fabirt.roka.R
import com.fabirt.roka.core.domain.model.Recipe
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailViewModel

fun Fragment.findMainNavController(): NavController {
    return Navigation.findNavController(
        requireActivity(),
        R.id.mainNavHostFragment
    )
}

fun Fragment.navigateToRecipeDetail(recipe: Recipe) {
    val viewModel by activityViewModels<RecipeDetailViewModel>()
    viewModel.requestRecipeInfo(recipe)
    val action = MainGraphDirections.actionGlobalRecipeDetailFragment()
    findMainNavController().navigate(action)
}