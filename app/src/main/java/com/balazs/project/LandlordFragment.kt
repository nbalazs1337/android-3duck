package com.balazs.project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.DataLandlord
import com.balazs.project.utils.LandlordAdapter

class LandlordFragment : Fragment() {
    private lateinit var rv_landlord: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landlord, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        rv_landlord = view.findViewById(R.id.rv_landlord_recom)
        rv_landlord.setHasFixedSize(true)
        rv_landlord.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )


        val imageList = MutableList(5) { i ->
            DataLandlord(
                R.drawable.hammer,
                "",
                "",
                "",
                R.drawable.ic_city,

            )

        }

        rv_landlord.adapter = LandlordAdapter(imageList)



    }



    }
