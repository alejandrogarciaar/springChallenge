package com.jgarcia.springchallenge.data.di

import com.jgarcia.springchallenge.data.dataSource.ForecastDataSource
import com.jgarcia.springchallenge.data.dataSource.impl.ForecastDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindForecastDataSource(forecastDataSourceImpl: ForecastDataSourceImpl): ForecastDataSource
}