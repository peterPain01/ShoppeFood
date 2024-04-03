import com.foodapp.data.model.User
import com.foodapp.data.repository.RetrofitClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/user/{id}")
    fun getPostById(@Path("id") postId: Int): Call<User>

    @POST("auth/signup")
    fun signUp(@Body user: User): Call<User>

    @POST("/auth/login")
    fun logIn(@Body user : User) : Call<User>
}