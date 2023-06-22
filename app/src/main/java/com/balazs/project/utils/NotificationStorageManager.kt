package com.balazs.project.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.balazs.project.data.model.Notification
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object NotificationStorageManager {
    private const val NOTIFICATION_PREFS_NAME = "notification_prefs"
    private const val NOTIFICATION_TITLE_KEY = "notification_title"
    private const val NOTIFICATION_MESSAGE_KEY = "notification_message"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(NOTIFICATION_PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveNotifications(context: Context, title: String, message: String) {
        val sharedPreferences = getSharedPreferences(context)
        val editor = sharedPreferences.edit()
        editor.putString("notification_title", title)
        editor.putString("notification_message", message)
        editor.apply()

    }

    fun loadNotification(context: Context): Notification? {
        val sharedPreferences = getSharedPreferences(context)
        val title = sharedPreferences.getString(NOTIFICATION_TITLE_KEY, null)
        val message = sharedPreferences.getString(NOTIFICATION_MESSAGE_KEY, null)

        return if (title != null && message != null) {
            Notification(title, message)
        } else {
            null
        }
    }
}
