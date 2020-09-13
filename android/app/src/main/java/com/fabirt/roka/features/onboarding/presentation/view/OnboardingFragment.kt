package com.fabirt.roka.features.onboarding.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fabirt.roka.R
import com.fabirt.roka.core.presentation.viewmodel.DataStoreViewModel
import com.fabirt.roka.features.onboarding.constants.onboardingItems
import com.fabirt.roka.features.onboarding.presentation.adapters.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : Fragment() {

    private val dataStoreViewModel: DataStoreViewModel by activityViewModels()
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
        adapter = ViewPagerAdapter(onboardingItems)
        viewPager.adapter = adapter
        dotsIndicator.setViewPager2(viewPager)

        // Remove overscroll
        val child = viewPager.getChildAt(0)
        (child as? RecyclerView)?.overScrollMode = View.OVER_SCROLL_NEVER

        registerOnChangeCallback()

        buttonStart.setOnClickListener {
            nextPage()
        }
    }

    private fun registerOnChangeCallback() {
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
    }

    private fun nextPage() {
        if (currentIndex >= 2) {
            dataStoreViewModel.writeOnboardingDidShow()
            val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
            findNavController().navigate(action)
        } else {
            viewPager.setCurrentItem(currentIndex + 1, true)
        }
    }
}