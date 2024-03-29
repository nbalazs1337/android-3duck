package com.balazs.project

import Adapter
import RentListingAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.api.Result
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.networking.RetrofitClient
import com.balazs.project.presentation.AddRentFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TenantFragment : Fragment(),AddRentFragment.AddRentListener {
    private lateinit var recomendedRecyclerView: RecyclerView
    private lateinit var newestRecyclerView: RecyclerView
    private lateinit var adapter: RentListingAdapter
    private  var adapter_usa: Adapter?=null
    private val rentListings: MutableList<RentListing> = mutableListOf()
    private lateinit var searchView: SearchView




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
        // Find the SearchView from the layout
        searchView = view.findViewById(R.id.search_view)
         // val recomendedAdapter = Adapter(propertyListings)
        // Set the adapter to the RecyclerView
        // recomendedRecyclerView.adapter = recomendedAdapter
        // Set the OnQueryTextListener for search events
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
        recomendedRecyclerView.adapter = adapter_usa

        loadRentListingsFromSharedPreferences()
        loadUsaListingsFromSharedPreferences()


        fetchDataFromAPI()


        val btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_add.setOnClickListener {
            openAddDataScreen()
        }

        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs2USA", Context.MODE_PRIVATE)
        val usaListingsJson = sharedPreferences.getString("usaListings", null)
        Log.d("SharedPreferences", "USA Listings: $usaListingsJson")




    }
    override fun onPause() {
        super.onPause()
        adapter.saveRentListingsToSharedPreferences(requireContext())
        adapter_usa?.saveUsaListingsToSharedPreferences(requireContext())
    }

    private fun fetchDataFromAPI() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefsUSA", Context.MODE_PRIVATE)
        val savedListingsJson = sharedPreferences.getString("usa_listings", null)

        if (!savedListingsJson.isNullOrEmpty()) {
            // Data is available in SharedPreferences, parse the JSON and use it
            val savedListings: List<Result> = Gson().fromJson(savedListingsJson, object : TypeToken<List<Result>>() {}.type)
            showData(savedListings)
        } else {
            // Data is not available in SharedPreferences, make the API request
            val retrofitService = RetrofitClient.realEstateApiService

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val response = retrofitService.getRentalPropertyListings("Detroit", "MI")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        val propertyListings = responseBody?.data?.home_search?.results ?: emptyList()
                        showData(propertyListings)
                        Log.d("api", "${response.code()}")
                        Log.d("api", "${propertyListings}")
                        // Save the fetched data to SharedPreferences
                        val listingsJson = Gson().toJson(propertyListings)
                        sharedPreferences.edit().putString("usa_listings", listingsJson).apply()
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
    }

    private fun showData(propertyListings: List<Result>) {
        // Display the data on the UI or perform any other necessary operations
        adapter_usa = Adapter(propertyListings)
        recomendedRecyclerView.adapter = adapter_usa
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
        // Set the first photo from the added photos to the RecyclerView's item
        if (rentListing.photoUrl.isNotEmpty()) {
            val firstPhoto = rentListing.photoUrl[0]
            Log.d("photo", "First Photo Tenant Fragment ${firstPhoto}")
            // Assuming you have a position to update, set the photoUri to the corresponding item in the adapter
            // Replace `position` with the actual position where you want to set the photo
            adapter.setPhotoUri(0, firstPhoto)
        }
    }

    private fun performSearch(query: String) {
        val adapter = newestRecyclerView.adapter as? RentListingAdapter
        adapter?.filter(query)
    }
    private fun loadRentListingsFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs2", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("rentListings", null)
        val type = object : TypeToken<List<RentListing>>() {}.type
        val rentListings = gson.fromJson<List<RentListing>>(json, type) ?: emptyList()

        adapter.setRentListings(rentListings)
    }

    private fun loadUsaListingsFromSharedPreferences() {
        val sharedPreferences = requireContext().getSharedPreferences("MyPrefs2USA", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("usaListings", null)
        val type = object : TypeToken<List<Result>>() {}.type
        val usaListings = gson.fromJson<List<Result>>(json, type) ?: emptyList()

       // adapter_usa?.setUsaListings(usaListings)
    }



}



