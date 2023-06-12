package com.balazs.project

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.DataLandlord
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.presentation.AddLandlordFragment
import com.balazs.project.presentation.AddRentFragment
import com.balazs.project.utils.LandlordAdapter
import com.balazs.project.utils.LandlordListingAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LandlordFragment : Fragment(),AddLandlordFragment.AddLandlordListener {

    private lateinit var rv_landlord_new: RecyclerView
    private lateinit var adapter: LandlordListingAdapter
    private lateinit var searchView: SearchView
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
        searchView = view.findViewById(R.id.search_view)





        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Perform the search action when the user submits the query
                performSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Perform filtering as the user types in the search query
                performSearch(newText)

                return true
            }
        })



        val imageList = MutableList(3) { i ->
            DataLandlord(
                R.drawable.hammer,
                "",
                "",
                "",
                R.drawable.ic_city,

            )

        }



        rv_landlord_new = view.findViewById(R.id.rv_landlord_new)
        rv_landlord_new.setHasFixedSize(true)
        rv_landlord_new.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        rv_landlord_new.adapter = adapter

       loadLandlordListingsFromSharedPreferences()


        val btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            openAddDataScreen()
        }


    }
    override fun onPause() {
        super.onPause()
        adapter.saveRentListingsToSharedPreferences(requireContext())


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

    private fun performSearch(query: String) {
        val adapter = rv_landlord_new.adapter as? LandlordListingAdapter
        adapter?.filter(query)
    }
    private fun loadLandlordListingsFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs2Landlord", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("landlordListings", null)
        val type = object : TypeToken<List<LandlordListing>>() {}.type
        val landlordListings = gson.fromJson<List<LandlordListing>>(json, type) ?: emptyList()

        adapter.setLandlordListings(landlordListings)
    }



}
