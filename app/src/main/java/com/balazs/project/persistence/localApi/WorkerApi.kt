package com.balazs.project.persistence.localApi

import com.balazs.project.persistence.dao.WorkerDao
import com.balazs.project.persistence.entity.WorkerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkerApi(private val workerDao: WorkerDao) {

    suspend fun addWorker(worker: WorkerEntity) {
        withContext(Dispatchers.IO) {
            workerDao.insertWorker(worker)
        }
    }

    suspend fun getAllWorkers(): List<WorkerEntity> {
        return withContext(Dispatchers.IO) {
            workerDao.getAllWorkers()
        }


        /* fun updateWorker(worker: WorkerDB) {
             workerDao.updateWorker(worker)
         }

         fun deleteWorker(worker: WorkerDB) {
             workerDao.deleteWorker(worker)
         }*/
    }

    suspend fun deleteAllWorkers() {
        withContext(Dispatchers.IO) {
            workerDao.deleteAllWorkers()
        }
    }
}
