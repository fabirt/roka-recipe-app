package com.fabirt.roka.core.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fabirt.roka.core.data.providers.DataStoreProvider
import kotlinx.coroutines.launch

class DataStoreViewModel @ViewModelInject constructor(
    private val dataStoreProvider: DataStoreProvider
) : ViewModel() {

    suspend fun readOnboardingDidShow() = dataStoreProvider.readOnboardingDidShow()

    fun writeOnboardingDidShow() {
        viewModelScope.launch {
            dataStoreProvider.writeOnboardingDidShow()
        }
    }
}