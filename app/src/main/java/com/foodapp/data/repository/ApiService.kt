import android.media.session.MediaSession.Token
import com.foodapp.data.model.Restaurant
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.AuthResponse
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.model.auth.Tokens
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("api/user/{id}")
    fun getUserById(@Path("id") postId: Int): Call<User>

    @POST("auth/signup")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("auth/login")
    fun logIn(@Body user : User) : Call<AuthResponse>

    @Headers(
        "Content-Type: application/json")
    @GET("api/restaurant")
    fun getAllRestaurant(
        @Header("User-ID") userId: String,
        @Header("Access-Token") accessToken: String
    ) : Call<Restaurant>
}