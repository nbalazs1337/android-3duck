package com.balazs.project.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.balazs.project.data.model.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationStorageManager {
    private const val NOTIFICATION_PREFS_NAME = "notification_prefs"
    private const val NOTIFICATION_LIST_KEY = "notification_list"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(NOTIFICATION_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveNotifications(context: Context, notifications: List<Notification>) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(notifications)
        editor.putString(NOTIFICATION_LIST_KEY, json)
        editor.apply()

    }
    fun loadNotifications(context: Context): List<Notification> {
    val sharedPreferences = getSharedPreferences(context)
    val json = sharedPreferences.getString(NOTIFICATION_LIST_KEY, null)

    return if (json != null) {
        // Convert the JSON string to a list of notifications
        val notifications = Gson().fromJson<List<Notification>>(
            json,
            object : TypeToken<List<Notification>>() {}.type
        )

        notifications
    } else {
        emptyList()
    }
}

    fun removeNotification(context: Context, notification: Notification) {
        val sharedPreferences = getSharedPreferences(context)
        val notifications = ArrayList<Notification>(loadNotifications(context))
        notifications.remove(notification)
        saveNotifications(context, notifications)
    }
}
