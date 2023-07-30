package com.balazs.project.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.balazs.project.persistence.entity.WorkerEntity


@Dao
interface WorkerDao {
    @Query("SELECT * FROM workers")
    suspend fun getAllWorkers(): List<WorkerEntity>

    @Insert
    suspend fun insertWorker(worker: WorkerEntity)

    @Query("DELETE FROM workers")
    suspend fun deleteAllWorkers()
}
/*
 @Update
    suspend fun updateWorker(worker: WorkerEntity)

    @Delete
    suspend fun deleteWorker(worker: WorkerDB)
*/


