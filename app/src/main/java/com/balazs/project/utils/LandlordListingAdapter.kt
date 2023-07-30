package com.balazs.project.utils
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.presentation.LandlordDetailActivity
import com.google.gson.Gson
import java.util.Locale
import java.util.UUID

class LandlordListingAdapter : RecyclerView.Adapter<LandlordListingAdapter.LandlordListingViewHolder>() {

    private val landlordListings: MutableList<LandlordListing> = mutableListOf()
    private val filteredLandlordListings: MutableList<LandlordListing> = mutableListOf()
    private val ratingMap: MutableMap<String, Pair<Float, Int>> = mutableMapOf()

    inner class LandlordListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views within the ViewHolder
        // For example:
        val title: TextView = itemView.findViewById(R.id.txt_title_notification)
        val name: TextView = itemView.findViewById(R.id.txt_name_notification)
        val price: TextView = itemView.findViewById(R.id.txt_price_landlord)
        val rating: TextView = itemView.findViewById(R.id.txt_rating_average_item)



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

        // Retrieve the rating from SharedPreferences based on the item ID
        val sharedPreferences = holder.itemView.context.getSharedPreferences("ItemRatings", Context.MODE_PRIVATE)
        val savedRating = sharedPreferences.getFloat("${landlordListing.itemId}-averageRating", 0.0f)
        Log.d("raating", "$savedRating")
        Log.d("raating", "${landlordListing.itemId}")


        // Update the TextView for the rating or average rating
        holder.rating.visibility = View.VISIBLE
        holder.rating.text = savedRating.toString()


        holder.price.text = landlordListing.price
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, LandlordDetailActivity::class.java)
            intent.putExtra("title", holder.title.text)
            intent.putExtra("name", holder.name.text)// pass any data to the next activity
            intent.putExtra("price", holder.price.text)// pass any data to the next activity
            intent.putExtra("itemId", landlordListing.itemId)// pass any data to the next activity
            // intent.putExtra("city", holder.title.text) // pass any data to the next activity
            //intent.putExtra("rooms", holder.title.text) // pass any data to the next activity
            holder.itemView.context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return filteredLandlordListings.size
    }

    fun addLandlordListing(landlordListing: LandlordListing) {

        val randomUUID = UUID.randomUUID().toString() // Generate a random ID
        val updatedLandlordListing = landlordListing.copy(itemId = randomUUID) // Update the itemId with the generated ID
        landlordListings.add(updatedLandlordListing)
        filteredLandlordListings.add(updatedLandlordListing)
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
