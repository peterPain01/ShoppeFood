package com.foodapp.viewmodel

import ApiService
import android.R
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Product
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateProductViewModel(context: Context, spinner: Spinner, sessionManager: SessionManager): ViewModel()  {
    var service = UserRepository(sessionManager).create(ApiService::class.java)
    var product: Product = Product();
    var spinner = spinner;
    var context = context
    var origin: String ?= null;
    var discount: String ?= null;
    private var categories: List<Category> ?= null;
    init {
        service.getAllCategories().enqueue(
            object : Callback<ApiResult<List<Category>>>{
                override fun onResponse(
                    call: Call<ApiResult<List<Category>>>,
                    response: Response<ApiResult<List<Category>>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        var spinnerItems : List<String> = listOf()
                        categories = body?.metadata;

                        categories?.forEach{
                            spinnerItems += it.name;
                        }
                        val adapter = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerItems)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spinner.setAdapter(adapter)
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("CreateShop", "Error creating shop: $errorMessage")
                    }
                }
                override fun onFailure(call: Call<ApiResult<List<Category>>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            }
        )
    }
    fun createProduct(image: File) {
        val Name = product.product_name?.toRequestBody("text/plain".toMediaTypeOrNull());
        val originPrice = origin?.toRequestBody("text/plain".toMediaTypeOrNull());
        val discountPrice = discount?.toRequestBody("text/plain".toMediaTypeOrNull());
        val description = product.product_description.toRequestBody("text/plain".toMediaTypeOrNull());
        var idCategory: String = ""
        categories?.forEach {
            if(it.name ==  spinner.selectedItem.toString()){
                idCategory = it._id;
            }
        }

        val category = idCategory.toRequestBody("text/plain".toMediaTypeOrNull());
        val requestFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("product_thumb", image.name, requestFile)

        if (Name != null && discountPrice != null && idCategory != null && originPrice != null) {
            service.createProduct(Name, description, discountPrice, category, originPrice, imagePart).enqueue(object :
                Callback<ApiResult<Product>> {
                override fun onResponse(
                    call: Call<ApiResult<Product>>,
                    response: Response<ApiResult<Product>>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()
                        Toast.makeText(context, "Success create", Toast.LENGTH_SHORT).show()
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("CreateShop", "Error creating shop: $errorMessage")
                    }
                }

                override fun onFailure(
                    call: Call<ApiResult<Product>>,
                    t: Throwable
                ) {
                    Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
                }
            })
        }
    }
}
