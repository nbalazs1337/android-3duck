package com.balazs.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.balazs.project.persistence.localApi.WorkerApi
import com.balazs.project.persistence.model.WorkerDB
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkerViewModel @Inject constructor(private val workerApi: WorkerApi) : ViewModel() {

    private val _workersLiveData = MutableLiveData<List<WorkerDB>>()
    val workersLiveData: LiveData<List<WorkerDB>> get() = _workersLiveData

    fun loadWorkers() {
        viewModelScope.launch {
            val workers = workerApi.getAllWorkers()
            _workersLiveData.postValue(workers)
        }
    }
}
