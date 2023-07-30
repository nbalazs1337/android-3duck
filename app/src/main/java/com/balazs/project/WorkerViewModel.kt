package com.balazs.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balazs.project.persistence.entity.WorkerEntity
import com.balazs.project.persistence.localApi.WorkerApi
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkerViewModel @Inject constructor(private val workerApi: WorkerApi) : ViewModel() {

    private val _workersLiveData = MutableLiveData<List<WorkerEntity>>()
    val workersLiveData: LiveData<List<WorkerEntity>> get() = _workersLiveData

    fun loadWorkers() {
        viewModelScope.launch {
            val workers = workerApi.getAllWorkers()
            _workersLiveData.postValue(workers)
        }
    }
}
