package com.balazs.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    lateinit var workerViewModel: WorkerViewModel
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
        // Fetch worker data using the WorkerApi
        workerViewModel = ViewModelProvider(this, workerViewModelFactory).get(WorkerViewModel::class.java)
                workerViewModel.workersLiveData.observe(viewLifecycleOwner) { workers ->
                    // Update UI with worker data
                    Log.d("tag2", "${workers}")
                }



        rv_worker = view.findViewById(R.id.rv_worker_recom)
        rv_worker.setHasFixedSize(true)
        rv_worker.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )


        val imageList = MutableList(5) { i ->
            DataWorker(
                R.drawable.hammer,
                "",
                "",
                "",
                "",
                "",
                R.drawable.ic_city,
                R.drawable.ic_star
            )

        }

        rv_worker.adapter = WorkerAdapter(imageList)



    }

    }
