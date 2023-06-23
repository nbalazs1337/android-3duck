package com.balazs.project.utils

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.Notification

class NotificationAdapter(private val notifications: ArrayList<Notification>) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txt_title_notification)
        val messageTextView: TextView = itemView.findViewById(R.id.txt_name_notification)
        val btnDelete: ImageView = itemView.findViewById(R.id.icon_delete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = notifications[position]

        holder.titleTextView.text = notification.title
        holder.messageTextView.text = notification.description

        holder.btnDelete.setOnClickListener {
            notifications.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, notifications.size)
            NotificationStorageManager.removeNotification(holder.itemView.context, notification)
        }

    }

    override fun getItemCount(): Int {
        return notifications.size
    }



}
