package com.balazs.project.utils

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.data.model.rv.WorkerListing
import com.balazs.project.presentation.LandlordDetailActivity
import com.balazs.project.presentation.WorkerDetailActivity

class WorkerListingAdapter : RecyclerView.Adapter<WorkerListingAdapter.WorkerListingViewHolder>() {

    private val workerListings: MutableList<WorkerListing> = mutableListOf()

    inner class WorkerListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views within the ViewHolder
        // For example:
        val title: TextView = itemView.findViewById(R.id.txt_title_worker)
        val city: TextView = itemView.findViewById(R.id.txt_city_worker)
        val price: TextView = itemView.findViewById(R.id.txt_price_worker)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerListingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.items_worker, parent, false)
        return WorkerListingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WorkerListingViewHolder, position: Int) {
        val rentListing = workerListings[position]

        // Bind the data to the views within the ViewHolder
        // For example:
        holder.title.text = rentListing.service
        holder.city.text = rentListing.city
        holder.price.text = rentListing.price
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, WorkerDetailActivity::class.java)
            holder.itemView.context.startActivity(intent)
    }}

    override fun getItemCount(): Int {
        return workerListings.size
    }

    fun addWorkerListing(workerListing: WorkerListing) {
        workerListings.add(workerListing)
        notifyDataSetChanged()
    }
}
