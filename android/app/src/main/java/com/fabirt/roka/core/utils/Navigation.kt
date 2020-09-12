package com.fabirt.roka.core.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.fabirt.roka.R

fun Fragment.findMainNavController(): NavController {
    return Navigation.findNavController(
        requireActivity(),
        R.id.mainNavHostFragment
    )
}