package com.fabirt.roka.core.utils

import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.fragment.app.Fragment

fun Fragment.configureStatusBar(isLight: Boolean = true) {
    val window = requireActivity().window
    val currentFlags = window.decorView.systemUiVisibility
    if (isLight) {
        window.decorView.systemUiVisibility =
            currentFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        window.decorView.systemUiVisibility =
            currentFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }
}

fun View.applyTopWindowInsets() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, insets.systemWindowInsetTop, 0, 0)
        v.layoutParams = params
        insets
    }
}
