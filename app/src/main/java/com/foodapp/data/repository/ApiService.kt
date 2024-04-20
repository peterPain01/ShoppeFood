import com.foodapp.data.model.Category
import com.foodapp.data.model.Shop
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.QueryName

interface ApiService {
    @GET("api/user/{id}")
    fun getUserById(@Path("id") postId: Int): Call<User>

    @POST("auth/signup")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("auth/login")
    fun logIn(@Body user : User) : Call<AuthResponse>

    @GET("categories")
    fun getAllCategories(): Call<List<Category>>

    @GET("shop/top-rated")
    fun getTopRated(
       @Query("limit") limit: Int = 10): Call<List<Shop>>

    @GET("user")
    fun getCurrentUserInfo(): Call<User>
}