package com.foodapp.data.repository

import ApiService
import com.foodapp.data.model.auth.SessionManager
import com.github.mikephil.charting.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


// NOTE: User service, set access token for every request
class UserRepository(val session: SessionManager) {
    private val service: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)
    public var retrofit: Retrofit? = null

    fun <T> create(service: Class<T>): T {
        if (retrofit == null) {
            val builder = OkHttpClient().newBuilder()
            builder.readTimeout(2, TimeUnit.SECONDS)
            builder.connectTimeout(2, TimeUnit.SECONDS)
            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
                builder.addInterceptor(interceptor)
            }
            val tokens = session.fetchAuthToken()
            builder.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request: Request =
                    chain.request().newBuilder()
                        .addHeader("x-authorization", tokens.accessToken!!)
                        .addHeader("x-client-id", tokens.userId!!)
                        .build()
                chain.proceed(request)
            })
            val client = builder.build()
            retrofit = Retrofit.Builder()
                .baseUrl(RetrofitClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!.create(service)
    }
}

