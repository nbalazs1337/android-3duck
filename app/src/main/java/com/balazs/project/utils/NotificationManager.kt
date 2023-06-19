package com.balazs.project.utils

import android.content.Context
import android.preference.PreferenceManager
import com.balazs.project.data.model.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationStorageManager {
    private const val NOTIFICATION_PREFS_KEY = "notification_prefs"
    private const val NOTIFICATION_LIST_KEY = "notification_list"

    fun saveNotifications(context: Context, notifications: List<Notification>) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = sharedPreferences.edit()

        // Convert the list of notifications to a JSON string
        val json = Gson().toJson(notifications)

        // Save the JSON string in SharedPreferences
        editor.putString(NOTIFICATION_LIST_KEY, json)
        editor.apply()
    }

    fun loadNotifications(context: Context): List<Notification> {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val json = sharedPreferences.getString(NOTIFICATION_LIST_KEY, null)

        return if (json != null) {
            // Convert the JSON string to a list of notifications
            Gson().fromJson(json, object : TypeToken<List<Notification>>() {}.type)
        } else {
            // Return an empty list if no notifications are stored
            emptyList()
        }
    }
}
