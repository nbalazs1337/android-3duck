package com.balazs.project.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private val notificationList = ArrayList<Notification>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back: ImageButton = findViewById(R.id.btn_back_notiScreen)
        btn_back.setOnClickListener {
            finish()
        }

        // Initialize the RecyclerView
        notificationRecyclerView = findViewById(R.id.rv_notifications)
        notificationRecyclerView.layoutManager = LinearLayoutManager(this)
        val notifications = NotificationStorageManager.loadNotifications(this)
        if (notifications != null) {
            notificationList.addAll(notifications)

        }
        // Set up the adapter
        notificationAdapter = NotificationAdapter(notificationList)
        notificationRecyclerView.adapter = notificationAdapter
        notificationAdapter.notifyDataSetChanged()


    }


    }

