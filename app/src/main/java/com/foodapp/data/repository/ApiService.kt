import com.foodapp.data.model.*
import com.foodapp.data.model.auth.AuthResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("api/user/{id}")
    fun getUserById(@Path("id") postId: Int): Call<User>

    @POST("auth/signup")
    fun signUp(@Body user: User): Call<AuthResponse>
    @GET("/product/{id}")
    fun getProductId(@Path("id") Id: String): Call<ApiResult<Product>>
    @POST("auth/login")
    fun logIn(@Body user : User) : Call<ApiResult<AuthResponse>>

    @GET("categories")
    fun getAllCategories(): Call<ApiResult<List<Category>>>

    @GET("shop/top-rated")
    fun getTopRated(
       @Query("limit") limit: Int = 10): Call<ApiResult<List<Shop>>>

    @GET("user")
    fun getCurrentUserInfo(): Call<User>

    @GET("shop/detail")
    fun getShopInfo(
        @Query("shopId") id: String
    ): Call<ApiResult<Shop>>

    @GET("product/all/shop")
    fun getShopProducts(
        @Query("shopId") id: String
    ): Call<ApiResult<List<Product>>>

    @POST("shop")
    fun createShop(@Body shop: Shop): Call<ApiResult<Shop>>
    @GET("shop/statistic/overall")
    fun getStatistic(): Call<ApiResult<DashBoard>>
    @GET("shop/publish")
    fun getProduct(): Call<ApiResult<List<Product>>>

    @POST("cart")
    fun addToCart(
        @Query("productId") productId: String
    ): Call<ApiResult<Nothing>>
}
