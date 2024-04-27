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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
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
                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            adapter?.toggleSelection(position)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }
                    //Toast.makeText(this, "Selected Items: $selectedItemsText", Toast.LENGTH_SHORT).show()
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
    fun CreateShop(phone: String, open_hour: String, close_hour: String, addresses: String, image: File) {
        val Name = shopInfo.name.toRequestBody("text/plain".toMediaTypeOrNull());
        val Description = shopInfo.description.toRequestBody("text/plain".toMediaTypeOrNull());
        val Phone = phone.toRequestBody("text/plain".toMediaTypeOrNull());
        val Open_hour = open_hour.toRequestBody("text/plain".toMediaTypeOrNull());
        val Close_hour = close_hour.toRequestBody("text/plain".toMediaTypeOrNull());

        val requestFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        val Categories = listOf(
            MultipartBody.Part.createFormData("category[]", "66235e91c99db74074241b5b"),
            MultipartBody.Part.createFormData("category[]", "66235e79c99db74074241b59")
        )

        val selectedItems = adapter?.getSelectedItems()
        val selectedItemsText = selectedItems?.map {  shopInfo.category [it] }?.joinToString(", ")

        // dang xu ly
        service.createShop(Name, Description, Phone, Open_hour, Close_hour, Categories, imagePart).enqueue(object : Callback<ApiResult<Shop>> {
            override fun onResponse(
                call: Call<ApiResult<Shop>>,
                response: Response<ApiResult<Shop>>
            ) {
                if (response.isSuccessful) {
                    val shopResult = response.body()
                    if (shopResult != null) {
                        Toast.makeText(context, "Shop created successfully!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Shop creation succeeded but no data received!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("CreateShop", "Error creating shop: $errorMessage")
                    Toast.makeText(context, "Failed to create shop: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ApiResult<Shop>>,
                t: Throwable
            ) {
                Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
}