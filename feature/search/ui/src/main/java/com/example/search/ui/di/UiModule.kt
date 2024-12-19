package com.example.search.ui.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.search.ui.navigation.SearchFeatureApi
import com.example.search.ui.navigation.SearchFeatureApiImpl

@InstallIn(SingletonComponent::class)
@Module
object UiModule {


    @Provides
    fun provideSearchFeatureApi(): SearchFeatureApi {
        return SearchFeatureApiImpl()
    }
}