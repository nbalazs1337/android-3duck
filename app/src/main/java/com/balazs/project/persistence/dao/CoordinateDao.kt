/*
package com.balazs.project.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.balazs.project.persistence.entity.CoordinateEntity

@Dao
interface CoordinateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(coordinate: CoordinateEntity)

    @Query("SELECT * FROM coordinates WHERE id = :id")
    suspend fun getCoordinateById(id: Long): CoordinateEntity?
}*/
