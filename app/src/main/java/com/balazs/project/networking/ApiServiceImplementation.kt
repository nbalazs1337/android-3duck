/*
package com.balazs.project.networking


import ApiService
import OAuth2Authenticator
import com.balazs.project.data.model.api.SearchResponse
import okhttp3.Authenticator
import retrofit2.Call
import javax.inject.Inject

class ApiServiceImplementation @Inject constructor(
    private val apiService: ApiService,
    private val authenticator: OAuth2Authenticator
): ApiService {


    override fun searchProperties(
        locale: String,
        maxItems: Int,
        numPage: Int,
        operation: String,
        order: String,
        propertyType: String,
        sort: String,
        t: Long,
        language: String,
        locationId: String
    ): Call<SearchResponse> {
        return apiService.searchProperties(
            locale,
            maxItems,
            numPage,
            operation,
            order,
            propertyType,
            sort,
            t,
            language,
            locationId
        )
    }
}
*/
