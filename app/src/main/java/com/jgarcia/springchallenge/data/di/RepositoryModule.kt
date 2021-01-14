package com.jgarcia.springchallenge.data.di

import com.jgarcia.springchallenge.data.repository.ForecastRepository
import com.jgarcia.springchallenge.data.repository.LocalForecastRepository
import com.jgarcia.springchallenge.data.repository.impl.ForecastRepositoryImpl
import com.jgarcia.springchallenge.data.repository.impl.LocalForecastRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindForecastRepository(forecastRepositoryImpl: ForecastRepositoryImpl): ForecastRepository

    @Binds
    abstract fun bindLocalForecastRepository(localForecastRepositoryImpl: LocalForecastRepositoryImpl): LocalForecastRepository
}