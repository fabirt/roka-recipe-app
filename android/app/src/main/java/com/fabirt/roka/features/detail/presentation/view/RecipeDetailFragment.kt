package com.fabirt.roka.features.detail.presentation.view

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.fabirt.roka.R
import com.fabirt.roka.core.utils.bindNetworkImage
import com.fabirt.roka.core.utils.configureStatusBar
import com.fabirt.roka.features.detail.presentation.adapters.RecipeDetailAdapter
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailState
import com.fabirt.roka.features.detail.presentation.viewmodel.RecipeDetailViewModel
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import kotlinx.android.synthetic.main.view_error.*
import kotlinx.android.synthetic.main.view_spin_indicator.*
import kotlinx.coroutines.launch

class RecipeDetailFragment : Fragment() {

    private val viewModel: RecipeDetailViewModel by activityViewModels()
    private val args: RecipeDetailFragmentArgs by navArgs()
    private lateinit var adapter: RecipeDetailAdapter
    private lateinit var pulseAnim: Animation

    companion object {
        private const val TAG = "RecipeDetailFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureTransitions()
        viewModel.motionProgress = 0F
        pulseAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_anim)

        args.id?.let {
            viewModel.requestRecipeInfo(it.toInt())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.motionProgress = motionLayout.progress
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

        motionLayout.transitionName = args.transitionName

        motionLayout.progress = viewModel.motionProgress
        adapter = RecipeDetailAdapter()
        val layoutManager = LinearLayoutManager(requireContext())
        rvDetails.adapter = adapter
        rvDetails.layoutManager = layoutManager
        applyWindowInsets()
        setupListeners()
        buildHeader()
    }

    private fun setupListeners() {
        viewModel.state.observe(viewLifecycleOwner, Observer(::buildView))

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        ivRecipe.setOnClickListener {
            openPhotoFragment()
        }

        btnRetry.setOnClickListener {
            viewModel.retryRecipeRequest(args.id)
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

    private fun buildHeader() {
        if (args.id == null) {
            viewModel.state.value?.recipe?.let { recipe ->
                bindNetworkImage(ivRecipe, recipe.imageUrl)
                tvName.text = recipe.title
                tvPeople.text = recipe.servings?.toString()
                tvTime.text = getString(R.string.minutes_label, recipe.readyInMinutes)
                tvScore.text = recipe.score?.toString()

                viewModel.isFavorite(recipe.id).observe(viewLifecycleOwner, Observer { isFavorite ->
                    var tint = requireContext().getColor(R.color.colorOnOverlay)
                    if (isFavorite) {
                        tint = requireContext().getColor(R.color.colorAccent)
                    }
                    ImageViewCompat.setImageTintList(ivSave, ColorStateList.valueOf(tint))
                })
            }
        } else {
            viewModel.state.observe(viewLifecycleOwner, Observer {state ->
                state.recipe?.let {recipe ->
                    bindNetworkImage(ivRecipe, recipe.imageUrl)
                    tvName.text = recipe.title
                    tvPeople.text = recipe.servings?.toString()
                    tvTime.text = getString(R.string.minutes_label, recipe.readyInMinutes)
                    tvScore.text = recipe.score?.toString()
                }
            })
            viewModel.isFavorite(args.id!!.toInt()).observe(viewLifecycleOwner, Observer { isFavorite ->
                var tint = requireContext().getColor(R.color.colorOnOverlay)
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
                tvErrorSubtitle.text = state.failure.translate(requireContext())
                spinView.visibility = View.GONE
                errorView.visibility = View.VISIBLE
                rvDetails.visibility = View.GONE
            }
            is RecipeDetailState.Success -> {
                rvDetails.scheduleLayoutAnimation()
                spinView.visibility = View.GONE
                errorView.visibility = View.GONE
                rvDetails.visibility = View.VISIBLE
                lifecycleScope.launch {
                    adapter.submitRecipeInfo(requireContext(), state.recipe!!)
                }
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
                val id = recipe.id.toString()
                val content = getString(R.string.share_content, recipe.title, url, id)
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

    private fun applyWindowInsets() {
        btnBack.setOnApplyWindowInsetsListener { v, insets ->
            motionLayout.getConstraintSet(R.id.start)?.apply {
                setMargin(v.id, ConstraintSet.TOP, insets.systemWindowInsetTop)
            }
            motionLayout.getConstraintSet(R.id.end)?.apply {
                setMargin(v.id, ConstraintSet.TOP, insets.systemWindowInsetTop)
            }
            insets
        }
        ivSave.setOnApplyWindowInsetsListener { v, insets ->
            motionLayout.getConstraintSet(R.id.start)?.apply {
                setMargin(v.id, ConstraintSet.TOP, insets.systemWindowInsetTop)
            }
            motionLayout.getConstraintSet(R.id.end)?.apply {
                setMargin(v.id, ConstraintSet.TOP, insets.systemWindowInsetTop)
            }
            insets
        }
    }

    private fun configureTransitions() {
        val duration = resources.getInteger(R.integer.page_transition_duration)
        val color = requireContext().getColor(R.color.colorBackground)
        val transition = MaterialContainerTransform().apply {
            this.duration = duration.toLong()
            containerColor = color
            drawingViewId = R.id.mainNavHostFragment
        }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}