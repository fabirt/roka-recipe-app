package com.fabirt.roka.core.data.providers

interface DataStoreProvider {

    suspend fun readOnboardingDidShow(): Boolean

    suspend fun writeOnboardingDidShow()
}