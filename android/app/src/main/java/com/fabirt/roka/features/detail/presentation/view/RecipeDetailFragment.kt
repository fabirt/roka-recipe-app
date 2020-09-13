package com.fabirt.roka.features.detail.presentation.view

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.applyTopWindowInsets
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.features.detail.presentation.adapters.RecipeDetailAdapter
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailState
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailViewModel
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*

class RecipeDetailFragment : Fragment() {

    private val viewModel: RecipeDetailViewModel by activityViewModels()
    private lateinit var adapter: RecipeDetailAdapter
    private lateinit var pulseAnim: Animation

    companion object {
        private const val TAG = "RecipeDetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pulseAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_anim)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureStatusBar(false)
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = RecipeDetailAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        rvDetails.adapter = adapter
        rvDetails.layoutManager = layoutManager
        setupListeners()
        buildStaticViews()
    }

    private fun setupListeners() {
        viewModel.state.observe(viewLifecycleOwner, Observer(::buildView))

        btnBack.applyTopWindowInsets()
        ivSave.applyTopWindowInsets()

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        ivRecipe.setOnClickListener {
            openPhotoFragment()
        }

        btnRetry.setOnClickListener {
            viewModel.retryRecipeRequest()
        }

        ivSave.setOnClickListener {
            it.startAnimation(pulseAnim)
            viewModel.saveOrDeleteRecipe()
        }

        ivShare.setOnClickListener {
            shareRecipe()
        }

        ivWeb.setOnClickListener {
            openWebView()
        }
    }

    private fun buildStaticViews() {
        viewModel.state.value?.recipe?.let { recipe ->
            bindNetworkImage(ivRecipe, recipe.imageUrl)
            tvName.text = recipe.title
            tvPeople.text = recipe.servings?.toString()
            tvTime.text = getString(R.string.minutes_label, recipe.readyInMinutes)
            tvScore.text = recipe.score?.toString()

            viewModel.isFavorite(recipe.id).observe(viewLifecycleOwner, Observer { isFavorite ->
                var tint = requireContext().getColor(R.color.colorSurface)
                if (isFavorite) {
                    tint = requireContext().getColor(R.color.colorAccent)
                }
                ImageViewCompat.setImageTintList(ivSave, ColorStateList.valueOf(tint))
            })
        }
    }

    private fun buildView(state: RecipeDetailState) {
        when (state) {
            is RecipeDetailState.Loading -> {
                spinView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                rvDetails.visibility = View.GONE
            }
            is RecipeDetailState.Error -> {
                tvErrorSubtitle.text = state.failure.toString()
                spinView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                rvDetails.visibility = View.GONE
            }
            is RecipeDetailState.Success -> {
                rvDetails.scheduleLayoutAnimation()
                spinView.visibility = View.GONE
                errorView.visibility = View.GONE
                rvDetails.visibility = View.VISIBLE
                adapter.submitRecipeInfo(requireContext(), state.recipe)
            }
        }
    }

    private fun openPhotoFragment() {
        viewModel.state.value?.recipe?.let { recipe ->
            val action = RecipeDetailFragmentDirections
                .actionRecipeDetailFragmentToPhotoRecipeFragment(recipe.imageUrl)
            val extras = FragmentNavigatorExtras(
                ivRecipe to PhotoRecipeFragment.SHARED_IMAGE
            )
            findNavController().navigate(action, extras)
        }
    }

    private fun shareRecipe() {
        viewModel.state.value?.recipe?.let { recipe ->
            val url = recipe.sourceUrl
            if (url != null && url.isNotEmpty()) {
                val title = recipe.title
                val content = getString(R.string.share_content, recipe.title, url)
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TITLE, title)
                    putExtra(Intent.EXTRA_TEXT, content)
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun openWebView(useBrowser: Boolean = false) {
        viewModel.state.value?.recipe?.let { recipe ->
            val url = recipe.sourceUrl
            if (url != null && url.isNotEmpty()) {
                if (useBrowser) {
                    val browserIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = Uri.parse(url)
                    }
                    startActivity(browserIntent)
                } else {
                    val action = RecipeDetailFragmentDirections
                        .actionRecipeDetailFragmentToWebDetailFragment(
                            url = url,
                            title = recipe.title
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }
}