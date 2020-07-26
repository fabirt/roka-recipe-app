package com.fabirt.roka.core.utils

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fabirt.roka.R

@BindingAdapter("imageUrl")
fun bindNetworkImage(imgView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder((R.drawable.category_placeholder))
                .error(R.drawable.not_available)
            )
            .into(imgView)
    }
}