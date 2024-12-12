import com.syzlnnuro.futourappv2.searchData.SearchRequest
import com.syzlnnuro.futourappv2.searchData.SearchResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface SearchApiService {
    @POST("search")
    suspend fun search(@Body request: SearchRequest): SearchResponse
}



