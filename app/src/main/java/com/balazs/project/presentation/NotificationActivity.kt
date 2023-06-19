package com.balazs.project.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.Notification
import com.balazs.project.utils.NotificationAdapter
import com.balazs.project.utils.NotificationStorageManager
import com.balazs.project.utils.TransparentStatusBarHandler

class NotificationActivity : AppCompatActivity() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = mutableListOf<Notification>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageButton = findViewById(R.id.btn_back_notiScreen)
        btn_back.setOnClickListener {
            finish()
        }

        // Initialize the RecyclerView
        notificationRecyclerView = findViewById(R.id.rv_notifications)
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the adapter
        notificationAdapter = NotificationAdapter(notificationList)
        notificationRecyclerView.adapter = notificationAdapter

        // Load and display the stored notifications
        loadNotificationsFromStorage()
    }

    private fun loadNotificationsFromStorage() {
        // Load the notifications from storage (e.g., SharedPreferences, database)
        // Add the notifications to the notificationList
        val notifications = NotificationStorageManager.loadNotifications(this)
        notificationList.clear()
        notificationList.addAll(notifications)


        // Notify the adapter that the data has changed
        notificationAdapter.notifyDataSetChanged()
    }

    private val notificationBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Get the title and message from the intent extras
            val title = intent.getStringExtra("title")
            val message = intent.getStringExtra("message")

            // Create a new notification item and add it to the list
            val notificationItem = Notification(title?:"", message?:"")
            notificationList.add(notificationItem)

            // Notify the adapter that a new item has been added
            notificationAdapter.notifyItemInserted(notificationList.size - 1)
        }
    }

    override fun onPause() {
        super.onPause()

        // Unregister the broadcast receiver
        unregisterReceiver(notificationBroadcastReceiver)
    }

    override fun onResume() {
        super.onResume()

        // Create an intent filter to receive the broadcast from MyFirebaseMessagingService
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.balazs.project.NOTIFICATION_RECEIVED")

        // Register the broadcast receiver
        registerReceiver(notificationBroadcastReceiver, intentFilter)
    }



}