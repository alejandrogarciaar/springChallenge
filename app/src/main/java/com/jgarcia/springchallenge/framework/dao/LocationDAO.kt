package com.jgarcia.springchallenge.framework.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jgarcia.springchallenge.domain.Forecast

@Dao
interface LocationDAO {
    @Query("SELECT * FROM LOCATION")
    suspend fun getAll(): List<Forecast.Location>

    @Insert
    suspend fun insert(location: Forecast.Location)

    @Delete
    suspend fun delete(location: Forecast.Location)
}