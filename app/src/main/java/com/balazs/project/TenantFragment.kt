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
import com.balazs.project.utils.SecondAdapter


class TenantFragment : Fragment() {
    private lateinit var recomendedRecyclerView: RecyclerView
    private lateinit var newestRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tenant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        recomendedRecyclerView = view.findViewById(R.id.rv_recomended)
        recomendedRecyclerView.setHasFixedSize(true)
        recomendedRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )


        newestRecyclerView = view.findViewById(R.id.rv_newest)
        newestRecyclerView.setHasFixedSize(true)
        newestRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )

        val imageList = MutableList(5) { i ->
            DataTenant(
                R.drawable.mock,
                "",
                "",
                "",
                R.drawable.ic_city,
                R.drawable.ic_star
            )

        }

        val imageList2 = MutableList(5) { i ->
            DataTenant2(
                R.drawable.tenant,
                "",
                "",
                "",
                "",
                R.drawable.ic_location

            )

        }


        newestRecyclerView.adapter = SecondAdapter(imageList2)
        recomendedRecyclerView.adapter = Adapter(imageList)



    }
}

