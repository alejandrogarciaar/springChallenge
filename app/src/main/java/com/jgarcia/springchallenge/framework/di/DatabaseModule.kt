package com.jgarcia.springchallenge.framework.di

import android.content.Context
import androidx.room.Room
import com.jgarcia.springchallenge.framework.dao.LocationDAO
import com.jgarcia.springchallenge.framework.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "appDatabase").build()
    }

    @Provides
    fun provideLocationDAO(appDatabase: AppDatabase): LocationDAO {
        return appDatabase.locationDAO()
    }
}