package com.foodapp.viewmodel

import ApiService
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Cart
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.AddressSpinnerAdapter
import com.foodapp.view.adapter.CartListAdapter
import retrofit2.Call
import retrofit2.Response

class CartViewModel(context: Context, val displayMsg: (String) -> Unit): ViewModel() {
    private var userService = UserRepository(SessionManager(context)).create(ApiService::class.java)
    var adapter: MutableLiveData<CartListAdapter> = MutableLiveData()
    var spinnerAdapter: MutableLiveData<AddressSpinnerAdapter> = MutableLiveData()
    var cart: MutableLiveData<Cart> = MutableLiveData()
    var totalPrice: MutableLiveData<Double> = MutableLiveData()

    init {
        userService.getUserAddresses().enqueue(object: retrofit2.Callback<ApiResult<List<UserAddress>>> {
            override fun onResponse(
                call: Call<ApiResult<List<UserAddress>>>,
                response: Response<ApiResult<List<UserAddress>>>
            ) {
                if (response.code() == 200) {
                    val addresses = response.body()?.metadata ?: listOf()
                    spinnerAdapter.value = AddressSpinnerAdapter(context, addresses)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<List<UserAddress>>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
        userService.getCart().enqueue(object: retrofit2.Callback<ApiResult<Cart>> {
            override fun onResponse(
                call: Call<ApiResult<Cart>>,
                response: Response<ApiResult<Cart>>
            ) {
                if (response.code() == 200) {
                    cart.value = response.body()?.metadata!!
                    totalPrice.value = cart.value?.totalPrice
                    adapter.value = CartListAdapter(cart.value?.cart_products!!, R.layout.cart_item, {
                        totalPrice.value = cart.value?.totalPrice
                    }, add = addProduct, reduce = reduceProduct, remove = removeProduct )
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Cart>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
    }

    val removeProduct: (String) -> Unit = {
        userService.removeProductFromCart(it).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
    }
    val addProduct: (String) -> Unit = {
        userService.increaseProductInCart(it).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
    }
    val reduceProduct: (String) -> Unit = {
        userService.reduceProductInCart(it).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
    }

}