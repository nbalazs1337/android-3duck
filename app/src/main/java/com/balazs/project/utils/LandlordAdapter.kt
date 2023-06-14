package com.balazs.project.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.DataLandlord

class LandlordAdapter(private val images: List<DataLandlord>) :
    RecyclerView.Adapter<LandlordAdapter.ImageViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_landlord, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        holder.iv_photo.setImageResource(R.drawable.worker)
        holder.txt_title.text = "Bathroom Tap Water"
        holder.txt_name.text = "Kovacs Attila"
        holder.txt_price.text = "50$"
        holder.iv_icon.setImageResource(R.drawable.ic_right)

    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_photo: ImageView = itemView.findViewById(R.id.iv_notification)
        val iv_icon: ImageView = itemView.findViewById(R.id.iv_right_landlord)
        val txt_title: TextView = itemView.findViewById(R.id.txt_title_notification)
        val txt_price: TextView = itemView.findViewById(R.id.txt_price_landlord)
        val txt_name: TextView = itemView.findViewById(R.id.txt_name_notification)

    }
}