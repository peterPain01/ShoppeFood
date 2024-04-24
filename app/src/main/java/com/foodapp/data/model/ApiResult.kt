package com.foodapp.data.model

data class ApiResult<T>(val message: String, val metadata: T)