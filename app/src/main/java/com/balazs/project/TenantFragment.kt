package com.balazs.project

import Adapter
import Photo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


       // newestRecyclerView.adapter = SecondAdapter(imageList2)
       // recomendedRecyclerView.adapter = Adapter(imageList)
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
                    val responseBodyString = responseBody?.string() // Convert the response body to a string
                    Log.d("api", "API response successful: $responseBodyString")
                    // Process the responseBody as needed
                    val propertyListings = parseResponseData(responseBodyString)
                    Log.d("api", "Property Listing: $responseBodyString")// Implement this method to parse the response
                    // Update the RecyclerView adapters with the fetched data
                    val recomendedAdapter = Adapter(propertyListings)
                    //val newestAdapter = SecondAdapter(propertyListings)
                    recomendedRecyclerView.adapter = recomendedAdapter
                    recomendedAdapter.notifyDataSetChanged()
               //     newestRecyclerView.adapter = newestAdapter
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
    private fun parseResponseData(responseBodyString: String?): List<Photo> {
        if (responseBodyString.isNullOrEmpty()) {
            // Handle the case where the response body is null or empty
            return emptyList()
        }
        val gson = Gson()

        val type: Type = object : TypeToken<List<Photo>>() {}.type
        val propertyListings: List<Photo> = gson.fromJson(responseBodyString, type)

        return propertyListings
    }
}



