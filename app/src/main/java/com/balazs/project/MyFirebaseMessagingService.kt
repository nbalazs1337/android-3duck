package com.balazs.project

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.balazs.project.presentation.HomeActivity
import com.balazs.project.presentation.NotificationActivity
import com.balazs.project.utils.NotificationStorageManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.balazs.project"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification() != null){
            val title = remoteMessage.notification!!.title!!
            val message = remoteMessage.notification!!.body!!
           // Log.d("lets", "yooo")
            generateNotification(this, title, message)

            // Store the notification in SharedPreferences
            notifyNotificationActivity(title, message)

        }

    }
    fun getRemoteView(title: String, message: String): RemoteViews {
        val remoteView = RemoteViews("com.balazs.project", R.layout.item_notification)
        remoteView.setTextViewText(R.id.txt_title_notification, title)
        remoteView.setTextViewText(R.id.txt_name_notification, message)
        remoteView.setImageViewResource(R.id.iv_notification, R.drawable.logopng)
        Log.d("lets", "yooo")

        return remoteView
    }

    fun generateNotification(context: Context, title: String, message: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        Log.d("lets", "$title")
        Log.d("lets", "$message")
        NotificationStorageManager.saveNotifications(context, title, message)


        var builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.drawable.ic_notification)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, message))

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }

        notificationManager.notify(0, builder.build())

    }

    private fun notifyNotificationActivity(title: String, message: String) {
        // Create an intent to notify the NotificationActivity
        val intent = Intent(this, NotificationActivity::class.java)
        intent.putExtra("title", title)
        Log.d("notii", "${title}")

        intent.putExtra("message", message)
        Log.d("notii", "${message}")

        // Send a broadcast to the NotificationActivity with the intent
        sendBroadcast(intent)
    }

}