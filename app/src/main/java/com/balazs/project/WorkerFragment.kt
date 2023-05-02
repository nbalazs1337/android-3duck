package com.balazs.project

import Adapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.DataTenant
import com.balazs.project.data.model.DataTenant2
import com.balazs.project.data.model.DataWorker
import com.balazs.project.utils.SecondAdapter
import com.balazs.project.utils.WorkerAdapter

class WorkerFragment : Fragment() {

    private lateinit var rv_worker: RecyclerView
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
