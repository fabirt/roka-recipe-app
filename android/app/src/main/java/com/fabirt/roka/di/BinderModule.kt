package com.fabirt.roka.di

import com.fabirt.roka.core.data.providers.DataStoreProvider
import com.fabirt.roka.core.data.providers.DataStoreProviderImpl
import com.fabirt.roka.core.domain.repository.RecipeRepository
import com.fabirt.roka.core.domain.repository.RecipeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class BinderModule {

    @Binds
    abstract fun bindRecipeRepository(
        recipeRepositoryImpl: RecipeRepositoryImpl
    ): RecipeRepository

    @Binds
    abstract fun bindDataStoreProvider(
        dataStoreProviderImpl: DataStoreProviderImpl
    ): DataStoreProvider
}