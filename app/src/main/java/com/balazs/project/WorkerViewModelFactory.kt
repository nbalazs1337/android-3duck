import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.balazs.project.WorkerViewModel
import com.balazs.project.persistence.localApi.WorkerApi

class WorkerViewModelFactory(private val workerApi: WorkerApi) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkerViewModel::class.java)) {
            return WorkerViewModel(workerApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
