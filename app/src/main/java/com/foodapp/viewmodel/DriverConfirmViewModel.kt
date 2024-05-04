package com.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import com.foodapp.data.model.auth.SessionManager

class DriverConfirmViewModel(val orderId: String, sessionManager: SessionManager): ViewModel() {
}