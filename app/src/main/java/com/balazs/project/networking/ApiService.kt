import com.balazs.project.data.model.api.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search")
    fun searchProperties(
        @Query("locale") locale: String,
        @Query("maxItems") maxItems: Int,
        @Query("numPage") numPage: Int,
        @Query("operation") operation: String,
        @Query("order") order: String,
        @Query("propertyType") propertyType: String,
        @Query("sort") sort: String,
        @Query("t") t: Long,
        @Query("language") language: String,
        @Query("locationId") locationId: String
    ): Call<SearchResponse>
}
