package com.balazs.project.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.DataTenant2
import com.balazs.project.presentation.RentDetailActivity

class SecondAdapter(private val images: List<DataTenant2>) :
    RecyclerView.Adapter<SecondAdapter.ImageViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_newest, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        holder.iv_photo.setImageResource(R.drawable.tenant)
        holder.txt_title.text = "Apartament Iris"
        holder.txt_subtitle.text = "3 camere/decomandat"
        holder.txt_city.text = "Cluj-Napoca"
        holder.txt_price.text = "350$"
        holder.iv_city.setImageResource(R.drawable.ic_location)

    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_photo: ImageView = itemView.findViewById(R.id.img_new)
        val iv_city: ImageView = itemView.findViewById(R.id.img_location)
        val txt_title: TextView = itemView.findViewById(R.id.txt_title_new)
        val txt_subtitle: TextView = itemView.findViewById(R.id.txt_subtitle_new)
        val txt_city: TextView = itemView.findViewById(R.id.txt_location_new)
        val txt_price: TextView = itemView.findViewById(R.id.txt_price_new)

    }
}