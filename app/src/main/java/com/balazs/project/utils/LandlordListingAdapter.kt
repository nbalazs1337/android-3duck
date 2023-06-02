package com.balazs.project.utils
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.presentation.LandlordDetailActivity
import com.balazs.project.presentation.RentDetailActivity
import com.google.gson.Gson
import java.util.Locale

class LandlordListingAdapter : RecyclerView.Adapter<LandlordListingAdapter.LandlordListingViewHolder>() {

    private val landlordListings: MutableList<LandlordListing> = mutableListOf()
    private val filteredLandlordListings: MutableList<LandlordListing> = mutableListOf()

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
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, LandlordDetailActivity::class.java)
            intent.putExtra("title", holder.title.text) // pass any data to the next activity
            // intent.putExtra("city", holder.title.text) // pass any data to the next activity
            //intent.putExtra("rooms", holder.title.text) // pass any data to the next activity
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredLandlordListings.size
    }

    fun addLandlordListing(landlordListing: LandlordListing) {
        landlordListings.add(landlordListing)
        filteredLandlordListings.add(landlordListing)
        notifyDataSetChanged()
    }
    fun filter(query: String) {
        filteredLandlordListings.clear()
        if (query.isNotEmpty()) {
            val searchQuery = query.toLowerCase(Locale.getDefault())
            filteredLandlordListings.addAll(landlordListings.filter {
                it.service.toLowerCase(Locale.getDefault()).contains(searchQuery)
            })
        } else {
            filteredLandlordListings.addAll(landlordListings)
        }
        notifyDataSetChanged()
    }

    fun setLandlordListings(landlordListings: List<LandlordListing>) {
        this.landlordListings.clear()
        this.landlordListings.addAll(landlordListings)
        filter("") // reapply the filter after setting the new list
    }
    fun saveRentListingsToSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs2Landlord", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(landlordListings)
        sharedPreferences.edit().putString("landlordListings", json).apply()
    }
}
