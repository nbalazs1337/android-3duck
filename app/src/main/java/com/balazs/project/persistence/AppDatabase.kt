package com.balazs.project.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.balazs.project.persistence.dao.WorkerDao
import com.balazs.project.persistence.model.WorkerDB

@Database(entities = [WorkerDB::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workerDao(): WorkerDao
}