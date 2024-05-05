package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.Product
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.MultipleChoiceSpinnerAdapter
import com.foodapp.view.adapter.myFoodAdapter
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.net.SocketTimeoutException
import kotlin.random.Random

class AdminViewModel (private val context: Context, private val spinner: Spinner){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    var shopInfo: Shop = Shop()
    var categoriesAdapter: MutableLiveData<MultipleChoiceSpinnerAdapter> = MutableLiveData();
    var adapter: MultipleChoiceSpinnerAdapter ?= null;
    init {
        RetrofitClient.retrofit.create(ApiService::class.java).getAllCategories().enqueue(object : Callback<ApiResult<List<Category>>> {
            override fun onResponse(call: Call<ApiResult<List<Category>>>, response: Response<ApiResult<List<Category>>>) {
                if (response.isSuccessful) {
                    val request = response.body()
                    var datas = request!!.metadata;
                    shopInfo.category = datas
                    adapter = MultipleChoiceSpinnerAdapter(context, datas.map { it.name })
                    categoriesAdapter.value = adapter
                    spinner.adapter = adapter
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(context, "Failed to get statistic: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Category>>>, t: Throwable) {
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
    fun CreateShop(phone: String, open_hour: String, close_hour: String, addresses: com.foodapp.data.model.UserAddress, image: File, avatar: File, onSuccess: () -> Unit) {
        val Name = shopInfo.name.toRequestBody("text/plain".toMediaTypeOrNull());
        val Description = shopInfo.description.toRequestBody("text/plain".toMediaTypeOrNull());
        val Phone = shopInfo.phone.toRequestBody("text/plain".toMediaTypeOrNull());
        val Open_hour = open_hour.toRequestBody("text/plain".toMediaTypeOrNull());
        val Close_hour = close_hour.toRequestBody("text/plain".toMediaTypeOrNull());
        val requestImage = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestImage)
        val requestAvatar = avatar.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val avatarPart = MultipartBody.Part.createFormData("avatar", avatar.name, requestAvatar)
        var Category: List<MultipartBody.Part> = listOf()

        val userRequestBody = createUserRequestBody(addresses)
        val userPartMap = hashMapOf("address" to userRequestBody)

        val selectedItems = adapter?.getSelectedItems()
        val index = 0;
        selectedItems?.forEach { item ->
            shopInfo.category?.forEach() { categories ->
                if(categories.name == item) {
                   val temp = MultipartBody.Part.createFormData("category[]", categories._id);
                    Category += temp;
                }
            }
        }

        service.createShop(Name, Description, Phone, Open_hour, Close_hour, userPartMap, Category, imagePart, avatarPart).enqueue(object : Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() == 201) {
                    onSuccess()
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("CreateShop", "Error creating shop: $errorMessage")
                    Toast.makeText(context, "Failed to create shop: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    onSuccess()
                } else {
                    Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
                    Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    fun createUserRequestBody(addresses: com.foodapp.data.model.UserAddress): RequestBody {
        val gson = Gson()
        val jsonString = gson.toJson(addresses)
        return jsonString.toRequestBody("application/json; charset=utf-8".toMediaType())
    }
}