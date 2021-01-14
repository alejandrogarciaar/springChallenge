package com.jgarcia.springchallenge.framework.persistance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.jgarcia.springchallenge.domain.Forecast

@Dao
interface LocationDAO {
    @Query("SELECT * FROM LOCATION")
    fun getAll(): List<Forecast.Location>

    @Delete
    fun delete(location: Forecast.Location)
}