package com.balazs.project

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.DataWorker
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.data.model.rv.WorkerListing
import com.balazs.project.persistence.localApi.WorkerApi
import com.balazs.project.presentation.AddRentFragment
import com.balazs.project.presentation.AddWorkerFragment
import com.balazs.project.utils.WorkerAdapter
import com.balazs.project.utils.WorkerListingAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkerFragment : Fragment(), AddWorkerFragment.AddWorkerListener {

    private lateinit var rv_worker: RecyclerView
    private lateinit var newestRecyclerView: RecyclerView
    private lateinit var adapter: WorkerListingAdapter
    private val workerListings: MutableList<WorkerListing> = mutableListOf()

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
        adapter = WorkerListingAdapter()
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

        newestRecyclerView = view.findViewById(R.id.rv_worker_new)
        newestRecyclerView.setHasFixedSize(true)
        newestRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        newestRecyclerView.adapter = adapter
        // Retrieve the saved data from shared preferences
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefsWorker", Context.MODE_PRIVATE)
        val title = sharedPreferences.getString("title", "")?: ""
        val service = sharedPreferences.getString("service", "")?: ""
        val price = sharedPreferences.getString("price", "")?: ""
        val city = sharedPreferences.getString("city", "")?: ""
        val description = sharedPreferences.getString("description", "")?: ""
        val workerListing = WorkerListing(title, city, price, description)
        adapter.addWorkerListing(workerListing)
        val btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            openAddDataScreen()
        }





    }

    private fun openAddDataScreen() {
        // Create an instance of the dialog fragment
        val addWorkerFragment = AddWorkerFragment().apply {
            addWorkerListener = this@WorkerFragment
        }
        addWorkerFragment.show(parentFragmentManager, "AddWorkerFragment")

    }


    override fun onWorkerAdded(workerListing: WorkerListing) {
        // Add the rentListing to your RecyclerView adapter here
        adapter.addWorkerListing(workerListing)
        adapter.notifyDataSetChanged()
    }

}
