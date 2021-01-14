package com.jgarcia.springchallenge.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.framework.dao.LocationDAO

@Database(entities = [Forecast.Location::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDAO(): LocationDAO
}