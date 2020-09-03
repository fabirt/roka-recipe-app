package com.fabirt.roka.features.onboarding.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.fabirt.roka.R
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = view.findNavController()
        buttonStart.setOnClickListener {
            val action = OnboardingFragmentDirections.actionOnboardingFragmentToHomeFragment()
            navController.navigate(action)
        }
    }
}