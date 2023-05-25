/*
package com.balazs.project.repository

import com.balazs.project.persistence.dao.AddressDao
import com.balazs.project.persistence.dao.CoordinateDao
import com.balazs.project.persistence.dao.WorkerDao
import com.balazs.project.persistence.entity.AddressEntity
import com.balazs.project.persistence.entity.CoordinateEntity
import com.balazs.project.persistence.entity.WorkerEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyRepository(private val workerDao: WorkerDao, private val addressDao: AddressDao, private val coordinateDao: CoordinateDao) {
    suspend fun insertWorker(worker: WorkerEntity) {
        withContext(Dispatchers.IO) {
            workerDao.insertWorker(worker)
        }
    }

    suspend fun getAllWorkers(): List<WorkerEntity> {
        return withContext(Dispatchers.IO) {
            workerDao.getAllWorkers()
        }
    }

    suspend fun insertAddress(address: AddressEntity) {
        withContext(Dispatchers.IO) {
            addressDao.insert(address)
        }
    }

    suspend fun getAllAddresses(): List<AddressEntity> {
        return withContext(Dispatchers.IO) {
            addressDao.getAllAddresses()
        }
    }

    suspend fun insertCoordinate(coordinate: CoordinateEntity) {
        withContext(Dispatchers.IO) {
            coordinateDao.insert(coordinate)
        }
    }

  */
/*  suspend fun getAllCoordinates(): List<CoordinateEntity> {
        return withContext(Dispatchers.IO) {
            coordinateDao.getAllCoordinates()
        }
    }*//*


    // Other repository functions...
}
*/
