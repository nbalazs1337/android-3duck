package com.balazs.project

import Adapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.data.model.api.Data
import com.balazs.project.data.model.api.HomeSearch
import com.balazs.project.data.model.api.Location
import com.balazs.project.data.model.api.PropertyResponse
import com.balazs.project.data.model.rv.DataTenant
import com.balazs.project.data.model.rv.DataTenant2
import com.balazs.project.networking.RetrofitClient
import com.balazs.project.utils.SecondAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.lang.reflect.Type


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


        fetchDataFromAPI()


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






}



