import com.foodapp.data.model.*
import com.foodapp.data.model.auth.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
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
    fun getCurrentUserInfo(): Call<ApiResult<User>>

    @GET("user/addresses")
    fun getUserAddresses(): Call<ApiResult<List<UserAddress>>>

    @GET("shop/detail")
    fun getShopInfo(
        @Query("shopId") id: String
    ): Call<ApiResult<Shop>>

    @GET("product/all/shop")
    fun getShopProducts(
        @Query("shopId") id: String
    ): Call<ApiResult<List<Product>>>
    @GET("shop/statistic/overall")
    fun getStatistic(): Call<ApiResult<DashBoard>>
    @GET("shop/publish")
    fun getProduct(): Call<ApiResult<List<Product>>>

    @POST("cart")
    fun addToCart(
        @Query("productId") productId: String,
        @Query("quantity") quantity: Int
    ): Call<ApiResult<Nothing>>

    @POST("cart")
    fun increaseProductInCart(
        @Query("productId") productId: String,
    ): Call<ApiResult<Nothing>>

    @PATCH("cart/reduce/product/{id}")
    fun reduceProductInCart(
        @Path("id") productId: String
    ): Call<ApiResult<Nothing>>

    @PATCH("cart/remove/product/{id}")
    fun removeProductFromCart(
        @Path("id") productId: String
    ): Call<ApiResult<Nothing>>

    @POST("cart/note")
    fun updateNote(
        @Query("note") note: String
    ): Call<ApiResult<Nothing>>

    @POST("auth/logout")
    fun logout(): Call<ApiResult<Nothing>>

    @PATCH("user")
    fun upadteUser(
        @Body userinfo: User
    ): Call<ApiResult<Nothing>>

    @Multipart
    @POST("shop")
    fun createShop(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("open_hour") openHour: RequestBody,
        @Part("close_hour") closeHour: RequestBody,
        @PartMap address: Map<String, @JvmSuppressWildcards RequestBody>,
        @Part category: List<MultipartBody.Part>,
        @Part image: MultipartBody.Part
    ): Call<ApiResult<Shop>>

    @GET("cart")
    fun getCart(): Call<ApiResult<Cart?>>

    @POST("order/checkout/cash")
    fun placeOrder(
        @Body address: UserAddress
    ): Call<ApiResult<Nothing>>

}

