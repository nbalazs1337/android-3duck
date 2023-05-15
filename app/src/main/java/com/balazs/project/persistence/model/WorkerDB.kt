package com.balazs.project.persistence.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workers")
data class WorkerDB(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val city: String,
    val type: String,
    val pricePerHour: Double
)
