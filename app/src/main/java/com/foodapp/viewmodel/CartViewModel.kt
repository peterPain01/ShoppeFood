package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Cart
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.helper.helper
import com.foodapp.view.adapter.AddressSpinnerAdapter
import com.foodapp.view.adapter.CartListAdapter
import retrofit2.Call
import retrofit2.Response


class CartViewModel(
    context: Context,
    val displayMsg: (String) -> Unit,
    val showError: (String, type: helper.PopupType) -> Unit
) : ViewModel() {
    private var userService = UserRepository(SessionManager(context)).create(ApiService::class.java)
    var adapter: MutableLiveData<CartListAdapter> = MutableLiveData()
    var spinnerAdapter: MutableLiveData<AddressSpinnerAdapter> = MutableLiveData()
    var cart: MutableLiveData<Cart> = MutableLiveData()
    var totalPriceRaw: Double = 0.0
        set(value) {
            field = value
            totalPrice.value = String.format("%s VND", helper.formatter(value))
            updateSum()
        }
    var shippingFeeRaw: Double = 0.0
        set(value) {
            field = value
            shippingFee.value = String.format("%s VND", helper.formatter(value))
            updateSum()
        }
    var totalPrice: MutableLiveData<String> = MutableLiveData("0.0")
    var shippingFee: MutableLiveData<String> = MutableLiveData("0.0")
    var sum: MutableLiveData<String> = MutableLiveData("0.0")

    private val contextLocal = context
    var paymentMethod: String = "cash"

    init {
        userService.getCart().enqueue(object : retrofit2.Callback<ApiResult<Cart?>> {
            override fun onResponse(
                call: Call<ApiResult<Cart?>>,
                response: Response<ApiResult<Cart?>>
            ) {
                if (response.code() == 200) {
                    cart.value = response.body()?.metadata!!
                    totalPriceRaw = cart.value?.totalPrice!!
                    adapter.value =
                        CartListAdapter(cart.value?.cart_products!!, R.layout.cart_item, {
                            totalPriceRaw = cart.value?.totalPrice!!
                        }, add = addProduct, reduce = reduceProduct, remove = removeProduct)
                    userService.getUserAddresses()
                        .enqueue(object : retrofit2.Callback<ApiResult<List<UserAddress>>> {
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

                            override fun onFailure(
                                call: Call<ApiResult<List<UserAddress>>>,
                                t: Throwable
                            ) {
                                displayMsg(t.message ?: t.toString())
                            }
                        })
                } else if (response.code() == 404) {
                    showError("Please add products to cart first!", helper.PopupType.Error)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Cart?>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }
        })
    }

    val removeProduct: (String) -> Unit = {
        userService.removeProductFromCart(it)
            .enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
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
        userService.increaseProductInCart(it)
            .enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
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
        userService.reduceProductInCart(it)
            .enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
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

    fun updateNote() {
        userService.updateNote(cart.value?.cart_note ?: "")
            .enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
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
    val onAddressSelected = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val addr = spinnerAdapter.value?.getItem(position)
            spinnerAdapter.value?.selectedItem = addr
            updateShippingFee(addr?.latlng!!)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
        }
    }

    fun updateShippingFee(pos: MapPosition) {
        userService.getShippingFee(lat = pos.lat, lng = pos .lng).enqueue(object: retrofit2.Callback<ApiResult<Double>> {
            override fun onResponse(
                call: Call<ApiResult<Double>>,
                response: Response<ApiResult<Double>>
            ) {
                if (response.code() == 200) {
                    shippingFeeRaw = response.body()?.metadata!!
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Double>>, t: Throwable) {
                displayMsg(t.message ?: t.toString())
            }

        })
    }

    fun placeOrder(_view: View) {
        // kiemt tra phuong thuc truoc khi order
        Log.d("FOODAPP:CartViewModel", spinnerAdapter.value?.selectedItem?.street.toString())
        if (spinnerAdapter.value?.selectedItem == null) {
            showError("Please update address in user info page first!", helper.PopupType.Error)
        } else {
            if (paymentMethod == "zalopay") {
                userService.placeOrderWithZalo(spinnerAdapter.value?.selectedItem!!)
                    .enqueue(object : retrofit2.Callback<ApiResult<String>> {
                        override fun onResponse(
                            call: Call<ApiResult<String>>,
                            response: Response<ApiResult<String>>
                        ) {
                            if (response.code() != 200) {
                                displayMsg(response.errorBody().toString())
                            } else {
                                val url = response.body()?.metadata.toString()
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                ContextCompat.startActivity(contextLocal, browserIntent, null)
                            }
                        }

                        override fun onFailure(call: Call<ApiResult<String>>, t: Throwable) {
                            displayMsg(t.message ?: t.toString())
                        }
                    })
            } else {
                userService.placeOrder(spinnerAdapter.value?.selectedItem!!)
                    .enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
                        override fun onResponse(
                            call: Call<ApiResult<Nothing>>,
                            response: Response<ApiResult<Nothing>>
                        ) {
                            if (response.code() != 201) {
                                displayMsg(response.errorBody().toString())
                            } else {
                                showError("Successfully", helper.PopupType.Info)
                            }
                        }

                        override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                            displayMsg(t.message ?: t.toString())
                        }
                    })
            }

        }
    }
    fun updateSum() {
        sum.value = String.format("%s VND", helper.formatter(totalPriceRaw + shippingFeeRaw))
    }
}