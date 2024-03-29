import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.presentation.RentDetailActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import java.util.Locale

class RentListingAdapter : RecyclerView.Adapter<RentListingAdapter.RentListingViewHolder>() {

    private val rentListings: MutableList<RentListing> = mutableListOf()
    private val filteredRentListings: MutableList<RentListing> = mutableListOf()

    inner class RentListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views within the ViewHolder
        // For example:
        val title: TextView = itemView.findViewById(R.id.txt_title)
        val city: TextView = itemView.findViewById(R.id.txt_city)
        val price: TextView = itemView.findViewById(R.id.txt_rooms)
        val img: ImageView = itemView.findViewById(R.id.iv_mock)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentListingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rent_listing, parent, false)
        return RentListingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RentListingViewHolder, position: Int) {
        //val rentListing = rentListings[position]
        val rentListing = filteredRentListings[position]

        // Bind the data to the views within the ViewHolder
        // For example:
        holder.title.text = rentListing.title
        holder.city.text = rentListing.neighborhood
        holder.price.text = rentListing.price

        /*  Glide.with(holder.itemView)
              .load(photoUri)
              .into(holder.photoImageView)*/
        Log.d("photo", "Photo from adapter 2${rentListing.photoUrl}")
        if (rentListing.photoUrl.isNotEmpty()) {
            val firstPhotoUrl = rentListing.photoUrl[0]
            try {
                holder.img.setImageURI(Uri.parse(firstPhotoUrl))
            } catch (e: SecurityException) {
                // Handle the security exception here, for example, show a default image or log an error
                holder.img.setImageResource(R.drawable.mock)
                Log.e("RentListingAdapter", "SecurityException: ${e.message}")
            }
        } else {
            // Set a default image or hide the ImageView
            holder.img.setImageResource(R.drawable.mock)
        }


        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RentDetailActivity::class.java)
            intent.putExtra("title", holder.title.text) // pass any data to the next activity
            intent.putExtra("city", holder.city.text) // pass any data to the next activity
            intent.putExtra("price", holder.price.text) // pass any data to the next activity
            val firstPhotoUri = rentListings[0].photoUrl.firstOrNull()
            intent.putExtra("photoUri", firstPhotoUri) // pass any data to the next activity
            val photosUrls = rentListing.photoUrl// Extract the photo URLs
            Log.d("idka", "${photosUrls}")
            intent.putStringArrayListExtra("photosUris", ArrayList(photosUrls))

            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return filteredRentListings.size
    }


    fun addRentListing(rentListing: RentListing) {
        rentListings.add(rentListing)
        filteredRentListings.add(rentListing)
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredRentListings.clear()
        if (query.isNotEmpty()) {
            val searchQuery = query.toLowerCase(Locale.getDefault())
            filteredRentListings.addAll(rentListings.filter {
                it.title.toLowerCase(Locale.getDefault()).contains(searchQuery)
            })
        } else {
            filteredRentListings.addAll(rentListings)
        }
        notifyDataSetChanged()
    }

    fun setRentListings(rentListings: List<RentListing>) {
        this.rentListings.clear()
        this.rentListings.addAll(rentListings)
        filter("") // reapply the filter after setting the new list
    }

    fun saveRentListingsToSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs2", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(rentListings)
        sharedPreferences.edit().putString("rentListings", json).apply()
    }

    fun setPhotoUri(position: Int, photoUri: String) {
        if (position in rentListings.indices) {
            val rentListing = rentListings[position]
            if (rentListing.photoUrl == null) {
                rentListing.photoUrl = mutableListOf() // Initialize as an empty list
            }
            rentListing.photoUrl.add(photoUri)
            notifyItemChanged(position)
        }
    }


}
