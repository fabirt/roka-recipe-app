package com.fabirt.roka.di

import android.content.Context
import com.fabirt.roka.core.data.database.AppDatabase
import com.fabirt.roka.core.data.network.client.RecipesApiClient
import com.fabirt.roka.core.data.network.services.RecipeService
import com.fabirt.roka.core.data.network.services.RecipeServiceFakeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRecipeService(): RecipeService = RecipesApiClient.createRecipeService()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = AppDatabase.createDatabase(context)

    @Provides
    @Singleton
    fun provideRecipeDao(database: AppDatabase) = database.recipeDao()
}