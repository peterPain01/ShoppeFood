import com.foodapp.data.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("/user/{id}")
    fun getPostById(@Path("id") postId: Int): Call<User>

    @POST("auth/signup")
    fun signUp(@Body user: User): Call<User>

    @POST("/auth/login")
    fun logIn(@Body user : User) : Call<User>

    @GET("/product/{id}")
    fun getProductId(@Path("id") Id: String): Call<Product>
}