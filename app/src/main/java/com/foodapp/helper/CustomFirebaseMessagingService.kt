package com.foodapp.helper

import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val broadcaster: LocalBroadcastManager = LocalBroadcastManager.getInstance(this)
        remoteMessage.notification?.let {
            if (it.title?.lowercase() == "new order") {
                val intent = Intent("new-order")
                intent.putExtra("orderId", it.body)
                broadcaster.sendBroadcast(intent)
            }
        }
    }


    private fun sendNotification(title: String, messageBody: String) {
        Log.d("FOODAPP:SendNotification", title)
        Log.d("FOODAPP:SendNotification", messageBody)
    }


    override fun onNewToken(p0: String) {
        Log.d("NotificationFCM", "Token: " + p0)
    }
}