/*
package com.balazs.project

import ApiService
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.balazs.project.data.model.api.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TestVM @Inject constructor(private val apiService: ApiService) : ViewModel() {

    val _propertySearchResult = MutableLiveData<SearchResponse?>()
    val propertySearchResult: LiveData<SearchResponse?> = _propertySearchResult

     val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun searchProperties() {
        val locale = "es"
        val maxItems = 20
        val numPage = 1
        val operation = "sale"
        val order = "publicationDate"
        val propertyType = "garages"
        val sort = "desc"
        val t = System.currentTimeMillis() / 1000
        val language = "es"
        val locationId = "0-EU-ES-28"

        val call = apiService.searchProperties(
            locale = locale,
            maxItems = maxItems,
            numPage = numPage,
            operation = operation,
            order = order,
            propertyType = propertyType,
            sort = sort,
            t = t,
            language = language,
            locationId = locationId
        )

        call.enqueue(object : Callback<SearchResponse?> {
            override fun onResponse(call: Call<SearchResponse?>, response: Response<SearchResponse?>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d("response", "${result}")
                    _propertySearchResult.value = result
                } else {
                    _error.value = "Failed to retrieve property search result"
                    Log.d("response", "${_error.value}")
                }
            }

            override fun onFailure(call: Call<SearchResponse?>, t: Throwable) {
                _error.value = "Network request failed"
                Log.d("response", "${_error.value}")
            }
        })
    }
}
*/
