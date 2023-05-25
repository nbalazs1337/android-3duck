import com.balazs.project.data.model.api.PropertyResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RealEstateApiService {
    @GET("v2/for-rent")
    suspend fun getRentalPropertyListings(
        @Query("city") city: String,
        @Query("state_code") stateCode: String,
        // Add other query parameters as needed
    ): Response<PropertyResponse>
}
