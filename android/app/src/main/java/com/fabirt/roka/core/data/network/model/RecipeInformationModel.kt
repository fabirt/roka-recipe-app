package com.fabirt.roka.core.data.network.model

import com.google.gson.annotations.SerializedName

data class RecipeInformationModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("sourceUrl")
    val sourceUrl: String?,
    @SerializedName("image")
    val imageUrl: String
)