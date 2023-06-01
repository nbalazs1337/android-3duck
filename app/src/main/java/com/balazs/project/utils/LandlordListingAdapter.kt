package com.balazs.project.utils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.data.model.rv.RentListing

class LandlordListingAdapter : RecyclerView.Adapter<LandlordListingAdapter.LandlordListingViewHolder>() {

    private val landlordListings: MutableList<LandlordListing> = mutableListOf()

    inner class LandlordListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views within the ViewHolder
        // For example:
        val title: TextView = itemView.findViewById(R.id.txt_title_landlord)
        val name: TextView = itemView.findViewById(R.id.txt_name_landlord)
        val price: TextView = itemView.findViewById(R.id.txt_price_landlord)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandlordListingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.items_landlord, parent, false)
        return LandlordListingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LandlordListingViewHolder, position: Int) {
        val landlordListing = landlordListings[position]

        // Bind the data to the views within the ViewHolder
        // For example:
        holder.title.text = landlordListing.service
        holder.name.text = landlordListing.name
        holder.price.text = landlordListing.price
    }

    override fun getItemCount(): Int {
        return landlordListings.size
    }

    fun addLandlordListing(landlordListing: LandlordListing) {
        landlordListings.add(landlordListing)
        notifyDataSetChanged()
    }
}
