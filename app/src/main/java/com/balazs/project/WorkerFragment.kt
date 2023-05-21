package com.balazs.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.DataWorker
import com.balazs.project.persistence.localApi.WorkerApi
import com.balazs.project.utils.WorkerAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkerFragment : Fragment() {

    private lateinit var rv_worker: RecyclerView

    @Inject
    lateinit var workerViewModelFactory: ViewModelProvider.Factory
    private val workerViewModel: WorkerViewModel by viewModels { workerViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as MyApplication).appComponent.inject(this)
        // Fetch worker data using the WorkerApi

        rv_worker = view.findViewById(R.id.rv_worker_recom)
        rv_worker.setHasFixedSize(true)
        rv_worker.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        workerViewModel.workersLiveData.observe(viewLifecycleOwner) { workers ->
            // Update UI with worker data
            val dataWorkers = workers.map { worker ->
                DataWorker(
                    // Map the properties from WorkerDB to DataWorker
                    // Modify the property assignments based on your WorkerDB class structure
                    iv_coverID = R.drawable.hammer,
                    txt_title = worker.name,
                    txt_city = worker.city,
                    txt_type = worker.type,
                    txt_price = "20",
                    iv_cityID = R.drawable.ic_location,


                    // Assign other properties as needed
                )

            }
            Log.d("tag3", "Hello + ${dataWorkers}")
            rv_worker.adapter = WorkerAdapter(dataWorkers)
        }

        // Load workers from the database
        workerViewModel.loadWorkers()





    }

}
