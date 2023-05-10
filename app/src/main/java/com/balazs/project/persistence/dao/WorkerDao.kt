package com.balazs.project.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.balazs.project.persistence.model.WorkerDB


@Dao
interface WorkerDao {
    @Query("SELECT * FROM workers")
    suspend fun getAllWorkers(): List<WorkerDB>

    @Insert
    suspend fun insertWorker(worker: WorkerDB)

   /* @Update
    suspend fun updateWorker(worker: WorkerDB)

    @Delete
    suspend fun deleteWorker(worker: WorkerDB)*/
}
