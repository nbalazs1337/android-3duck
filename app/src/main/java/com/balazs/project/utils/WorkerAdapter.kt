package com.balazs.project.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.DataWorker

class WorkerAdapter(private val images: List<DataWorker>) :
    RecyclerView.Adapter<WorkerAdapter.ImageViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_worker, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        val dataWorker = images[position]

        holder.iv_photo.setImageResource(dataWorker.iv_coverID)
        holder.txt_title.text = dataWorker.txt_title
        holder.txt_type.text = dataWorker.txt_type
        holder.txt_price.text = dataWorker.txt_price
        holder.iv_city.setImageResource(dataWorker.iv_cityID)
        holder.btn_apply.text = "apply"
    }
    override fun getItemCount(): Int {
        return images.size
    }
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_photo: ImageView = itemView.findViewById(R.id.iv_new)
        val iv_city: ImageView = itemView.findViewById(R.id.img_location)
        val txt_title: TextView = itemView.findViewById(R.id.txt_title_worker)
        val txt_type: TextView = itemView.findViewById(R.id.txt_type_worker)
        val txt_price: TextView = itemView.findViewById(R.id.txt_price_worker)
        val btn_apply: Button = itemView.findViewById(R.id.button)

    }
}