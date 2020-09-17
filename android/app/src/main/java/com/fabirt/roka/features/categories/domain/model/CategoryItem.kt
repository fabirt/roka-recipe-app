package com.fabirt.roka.features.categories.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItem (
    val type: String,
    val name: String,
    val imageUrl: String
) : Parcelable