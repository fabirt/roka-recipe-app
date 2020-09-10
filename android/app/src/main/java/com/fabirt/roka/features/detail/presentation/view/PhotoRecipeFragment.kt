package com.fabirt.roka.features.detail.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.bindNetworkImage
import kotlinx.android.synthetic.main.fragment_photo_recipe.*

class PhotoRecipeFragment : Fragment() {
    private val args: PhotoRecipeFragmentArgs by navArgs()

    companion object {
        const val SHARED_IMAGE = "recipeImage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater
            .from(context)
            .inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindNetworkImage(ivRecipe, args.imageUrl)
    }
}