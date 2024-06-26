import com.foodapp.data.model.*
import com.foodapp.data.model.auth.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
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
    fun getPublish(): Call<ApiResult<List<Product>>>

    @GET("shop/drafts")
    fun getUnPublish(): Call<ApiResult<List<Product>>>

    @GET("shop/product/all")
    fun getAllProduct(): Call<ApiResult<List<Product>>>


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
        @Part image: MultipartBody.Part,
        @Part avatar: MultipartBody.Part
    ): Call<ApiResult<Nothing>>

    @GET("cart")
    fun getCart(): Call<ApiResult<Cart?>>

    @POST("order/checkout/cash")
    fun placeOrder(
        @Body address: UserAddress
    ): Call<ApiResult<Nothing>>

    @POST("order/checkout/zalopay")
    fun placeOrderWithZalo(
        @Body address: UserAddress
    ): Call<ApiResult<String>>


    @GET("comment/all/shop")
    fun getComment(): Call<ApiResult<List<Review>>>

    // SEARCH - API
    @GET("shop/suggest")
    fun getRelatedSearchString(
        @Query("keySearch") keySearch: String,
    ): Call<ApiResult<List<String>>>

    @GET("shop/related")
    fun getShopBySearchString(
        @Query("keySearch") keySearch: String,
    ): Call<ApiResult<List<Shop>>>

    // LIKE - API
    @GET("user/liked/shop")
    fun getShopUserLiked(
        @Query("sortBy") sortBy: String,
    ): Call<ApiResult<List<Shop>>>

    @POST("user/like/shop")
    fun userShopLike(
        @Query("shopId") shopId : String,
    ): Call<ApiResult<Nothing>>
    @POST("user/unlike/shop")
    fun userShopUnlike(
        @Query("shopId") shopId : String,
    ): Call<ApiResult<Nothing>>

    @GET("user/overview")
    fun getUserOverview(): Call<ApiResult<UserOverview>>

    @GET("order/on-going")
    fun getOnGoingOrder(): Call<ApiResult<List<Order>>>

    @GET("order/success")
    fun getSuccessOrder(): Call<ApiResult<List<Order>>>

    @POST("shipper/location/update")
    fun updateAddress(
        @Body address: MapPosition
    ): Call<ApiResult<Nothing>>

    @POST("shipper/token/save")
    fun updateToken(
        @Query("token") token: String
    ): Call<ApiResult<Nothing>>

    @GET("order/detail")
    fun getOrderById(
        @Query("orderId") id: String
    ): Call<ApiResult<Order>>

    @POST("shipper/order/confirm")
    fun confirmOrder(
        @Query("orderId") id: String
    ): Call<ApiResult<Nothing>>

    @GET("shipper/")
    fun getShipperInfo(
    ): Call<ApiResult<Shipper>>

    @Multipart
    @PATCH("user")
    fun updateUser(
        @Part("fullname") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("bio") bio: RequestBody,
        @Part avatar: MultipartBody.Part?
    ): Call<ApiResult<User>>

    @POST("shipper/order/finish")
    fun finishOrder(
        @Query("orderId") orderId: String
    ): Call<ApiResult<Nothing>>

    @POST("shipper/recharge")
    fun rechargeShipper(
        @Query("amount") amount: Number
    ): Call<ApiResult<String>>


    @Multipart
    @POST("shop/product")
    fun createProduct(
        @Part("product_name") name: RequestBody,
        @Part("product_description") description: RequestBody,
        @Part("product_discounted_price") discounted: RequestBody,
        @Part("product_category") category: RequestBody,
        @Part("product_original_price") price: RequestBody,
        @Part product_thumb: MultipartBody.Part
    ): Call<ApiResult<Product>>

    @GET("/shop/statistic/order/pending")
    fun getAllPending(): Call<ApiResult<List<Running>>>

    @GET("user/shippingFee")
    fun getShippingFee(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double
    ): Call<ApiResult<Double>>

    @Multipart
    @POST("shipper/create")
    fun createShipper(
        @Part("fullname") name: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("license_plate_number") license: RequestBody,
        @Part avatar: MultipartBody.Part,
        @Part vehicle_image: MultipartBody.Part
    ): Call<ApiResult<Nothing>>

    @POST("/shop/publish")
    fun publish(
        @Query("productId") shopId : String,
    ): Call<ApiResult<Nothing>>

    @POST("/shop/un-publish")
    fun unPublish(
        @Query("productId") shopId : String,
    ): Call<ApiResult<Nothing>>

    @GET("state")
    fun setState(
        @Query("state") state: String
    ): Call<ApiResult<Nothing>>

    @GET("shipper/revenue/overview")
    fun getShipperRevenueOverview(): Call<ApiResult<ShipperOverview>>

    @GET("shipper/history/orders")
    fun getShipperHistoryOrders(): Call<ApiResult<List<Order>>>

    @GET("shop/all")
    fun getAllShops(): Call<ApiResult<List<Shop>>>

    @POST("comment/user/shop")
    fun postComment(
        @Body comment: Review
    ): Call<ApiResult<Nothing>>
}