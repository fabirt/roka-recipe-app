package com.fabirt.roka.core.data.providers

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.fabirt.roka.core.constants.K
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreProviderImpl @Inject constructor(
    @ApplicationContext context: Context
) : DataStoreProvider {

    private val dataStore = context.createDataStore(K.SETTINGS_DATA_STORE_NAME)
    private val onboardingDidShowKey = preferencesKey<Boolean>(K.ONBOARDING_DID_SHOW_KEY)

    override suspend fun readOnboardingDidShow(): Boolean {
        val flow = dataStore.data.map { preferences ->
            preferences[onboardingDidShowKey] ?: false
        }
        return flow.first()
    }

    override suspend fun writeOnboardingDidShow() {
        dataStore.edit { preferences ->
            preferences[onboardingDidShowKey] = true
        }
    }
}