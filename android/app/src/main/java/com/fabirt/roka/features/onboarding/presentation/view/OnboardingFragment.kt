package com.fabirt.roka.features.onboarding.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fabirt.roka.R
import com.fabirt.roka.features.onboarding.domain.model.OnboardingItem
import com.fabirt.roka.features.onboarding.presentation.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnboardingFragment : Fragment() {

    private lateinit var adapter: ViewPagerAdapter
    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
            OnboardingItem(
                R.drawable.onboarding_img_1,
                R.string.onboarding_title_1,
                R.string.onboarding_body_1
            ), OnboardingItem(
                R.drawable.onboarding_img_1,
                R.string.onboarding_title_1,
                R.string.onboarding_body_1
            ), OnboardingItem(
                R.drawable.onboarding_img_1,
                R.string.onboarding_title_1,
                R.string.onboarding_body_1
            )
        )
        adapter = ViewPagerAdapter(items)
        viewPager.adapter = adapter
        dotsIndicator.setViewPager2(viewPager)

        // Remove overscroll
        val child = viewPager.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                currentIndex = position
            }
        })
        val navController = view.findNavController()
        buttonStart.setOnClickListener {
            if (currentIndex >= 2) {
                val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
                navController.navigate(action)
            } else {
                viewPager.setCurrentItem(currentIndex + 1, true)
            }
        }
    }
}