package com.fabirt.roka.core.utils

import android.os.Build
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fabirt.roka.R

fun Fragment.configureStatusBar(isLight: Boolean = true) {
    val window = requireActivity().window
    var flags = window.decorView.systemUiVisibility
    flags = if (isLight) {
        flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    } else {
        flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    window.navigationBarColor = requireContext().getColor(R.color.colorSurface)
    window.decorView.systemUiVisibility = flags
}

fun View.applyTopWindowInsets() {
    setOnApplyWindowInsetsListener { v, insets ->
        val params = v.layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, insets.systemWindowInsetTop, 0, 0)
        v.layoutParams = params
        insets
    }
}
