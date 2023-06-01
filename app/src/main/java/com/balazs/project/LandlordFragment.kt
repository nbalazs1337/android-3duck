package com.balazs.project

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.DataLandlord
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.presentation.AddLandlordFragment
import com.balazs.project.presentation.AddRentFragment
import com.balazs.project.utils.LandlordAdapter
import com.balazs.project.utils.LandlordListingAdapter

class LandlordFragment : Fragment(),AddLandlordFragment.AddLandlordListener {
    private lateinit var rv_landlord: RecyclerView
    private lateinit var rv_landlord_new: RecyclerView
    private lateinit var adapter: LandlordListingAdapter
    private val landlordListings: MutableList<LandlordListing> = mutableListOf()
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
        adapter = LandlordListingAdapter()
        rv_landlord = view.findViewById(R.id.rv_landlord_recom)
        rv_landlord.setHasFixedSize(true)
        rv_landlord.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )


        val imageList = MutableList(3) { i ->
            DataLandlord(
                R.drawable.hammer,
                "",
                "",
                "",
                R.drawable.ic_city,

            )

        }

        rv_landlord.adapter = LandlordAdapter(imageList)

        rv_landlord_new = view.findViewById(R.id.rv_landlord_new)
        rv_landlord_new.setHasFixedSize(true)
        rv_landlord_new.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rv_landlord_new.adapter = adapter
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefsLandlord", Context.MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "")?: ""
        val service = sharedPreferences.getString("service", "")?: ""
        val price = sharedPreferences.getString("price", "")?: ""
        val experience = sharedPreferences.getString("experience", "")?: ""
        val description = sharedPreferences.getString("description", "")?: ""

        val landlordListing = LandlordListing(name, service, price, experience, description)
        adapter.addLandlordListing(landlordListing)

        val btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            openAddDataScreen()
        }


    }



    private fun openAddDataScreen() {
        // Create an instance of the dialog fragment
        val addLandlordFragment = AddLandlordFragment().apply {
            addLandlordListener = this@LandlordFragment
        }
        addLandlordFragment.show(parentFragmentManager, "AddLandlordFragment")

    }


    override fun onLandlordAdded(landlordListing: LandlordListing) {
        // Add the rentListing to your RecyclerView adapter here
        adapter.addLandlordListing(landlordListing)
        adapter.notifyDataSetChanged()
    }


    }
