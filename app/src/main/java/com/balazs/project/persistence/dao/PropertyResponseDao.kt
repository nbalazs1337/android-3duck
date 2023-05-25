/*
package com.balazs.project.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.balazs.project.persistence.entity.PropertyResponseEntity

@Dao
interface PropertyResponseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(propertyResponse: PropertyResponseEntity)

    // Add other necessary queries for data retrieval

    @Query("SELECT * FROM property_response")
    suspend fun getAllPropertyResponses(): List<PropertyResponseEntity>
}
*/
