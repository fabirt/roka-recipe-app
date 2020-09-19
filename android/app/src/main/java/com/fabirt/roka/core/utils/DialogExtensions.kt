package com.fabirt.roka.core.utils

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

fun Fragment.showDialog(
    @StringRes titleId: Int,
    message: String,
    @StringRes positiveTextId: Int,
    @StringRes negativeTextId: Int,
    onConfirm: (() -> Unit)?,
    onCancel: (() -> Unit)?
) {
    AlertDialog.Builder(requireContext())
        .setTitle(titleId)
        .setMessage(message)
        .setPositiveButton(positiveTextId) { _, _ ->
            onConfirm?.invoke()
        }
        .setNegativeButton(negativeTextId) { _, _ ->
            onCancel?.invoke()
        }
        .create()
        .show()
}