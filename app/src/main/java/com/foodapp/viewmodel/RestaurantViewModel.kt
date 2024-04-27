package com.foodapp.viewmodel

import ApiService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Product
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.ProductGridViewHolder
import retrofit2.Call
import retrofit2.Response

class RestaurantViewModel(val shopId: String, val displayError: (String?) -> Unit, displayImage: (String?) -> Unit): ViewModel() {
    var shop: MutableLiveData<Shop> = MutableLiveData(Shop(""))
    var categoryAdapter: MutableLiveData<HorizontalAdapter> = MutableLiveData()
    var foodGridAdapter: MutableLiveData<GridAdapter<Product>> = MutableLiveData()

    private val service = RetrofitClient.retrofit.create(ApiService::class.java)
    init {
        service.getShopInfo(shopId).enqueue(object: retrofit2.Callback<ApiResult<Shop>> {
            override fun onResponse(
                call: Call<ApiResult<Shop>>,
                response: Response<ApiResult<Shop>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    val shopInfo = body!!.metadata
                    shop.value = shopInfo
                    categoryAdapter.value = HorizontalAdapter(shop.value!!.category!!, R.layout.item_horizontal)
                    displayImage(shop.value!!.image)
                    // TODO: call get all foods

                    service.getShopProducts(shop.value!!._id).enqueue(object: retrofit2.Callback<ApiResult<List<Product>>> {
                        override fun onResponse(
                            call: Call<ApiResult<List<Product>>>,
                            response: Response<ApiResult<List<Product>>>
                        ) {
                            if (response.code() == 200) {
                                val products = response.body()!!.metadata
                                foodGridAdapter.value = GridAdapter(products, R.layout.item_grid_checkout, ::ProductGridViewHolder)
                            } else {
                                displayError(response.body()?.message)
                            }
                        }

                        override fun onFailure(call: Call<ApiResult<List<Product>>>, t: Throwable) {
                            displayError(t.message)
                        }
                    })

                } else {
                    displayError(response.body()?.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Shop>>, t: Throwable) {
                displayError(t.message)
            }
        })
    }
}