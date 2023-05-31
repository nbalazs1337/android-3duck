package com.balazs.project

import Adapter
import RentListingAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.networking.RetrofitClient
import com.balazs.project.presentation.AddRentFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TenantFragment : Fragment(),AddRentFragment.AddRentListener {
    private lateinit var recomendedRecyclerView: RecyclerView
    private lateinit var newestRecyclerView: RecyclerView
    private lateinit var adapter: RentListingAdapter
    private val rentListings: MutableList<RentListing> = mutableListOf()
    



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
        adapter = RentListingAdapter()
         // val recomendedAdapter = Adapter(propertyListings)
        // Set the adapter to the RecyclerView
        // recomendedRecyclerView.adapter = recomendedAdapter

        recomendedRecyclerView = view.findViewById(R.id.rv_recomended)
        recomendedRecyclerView.setHasFixedSize(true)
        recomendedRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        newestRecyclerView = view.findViewById(R.id.rv_new)
        newestRecyclerView.setHasFixedSize(true)
        newestRecyclerView.setLayoutManager(
            LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        newestRecyclerView.adapter = adapter


        //fetchDataFromAPI()
        val btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            openAddDataScreen()
        }


    }

    private fun fetchDataFromAPI() {
        val retrofitService = RetrofitClient.realEstateApiService

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = retrofitService.getRentalPropertyListings("Detroit", "MI")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("api", "API response successful: $responseBody")
                    val propertyListings = responseBody?.data?.home_search?.results ?: emptyList()
                    Log.d("api", "API response successful: $propertyListings")
                    if (propertyListings.isNotEmpty()) {
                        val recomendedAdapter = Adapter(propertyListings)
                        // Set the adapter to the RecyclerView
                        recomendedRecyclerView.adapter = recomendedAdapter
                        Log.d("Adapter", "${recomendedAdapter.itemCount}")
                        Log.d("Adapter", "Data size: ${propertyListings.size}")
                    } else {
                        // Handle the case where the response body is empty
                        Log.d("recycler", "There is no data")
                    }
                } else {
                    // Handle API error
                    Log.e("api", "API request failed with code: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("api", "Error fetching data from API: ${e.message}")
                // Handle network or other exceptions
            }
        }
    }



    private fun openAddDataScreen() {
        // Create an instance of the dialog fragment
        val addRentFragment = AddRentFragment().apply {
            addRentListener = this@TenantFragment
        }
        addRentFragment.show(parentFragmentManager, "AddRentFragment")

    }


    override fun onRentAdded(rentListing: RentListing) {
        // Add the rentListing to your RecyclerView adapter here
        adapter.addRentListing(rentListing)
        adapter.notifyDataSetChanged()
    }

}



