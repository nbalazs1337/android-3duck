package com.balazs.project.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "property_response")
data class PropertyResponseEntity(
    @PrimaryKey val id: String,
    // Include other fields you want to cache from PropertyResponse
)

