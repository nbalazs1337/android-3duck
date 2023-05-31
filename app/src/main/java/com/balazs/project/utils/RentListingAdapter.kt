import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.RentListing

class RentListingAdapter : RecyclerView.Adapter<RentListingAdapter.RentListingViewHolder>() {

    private val rentListings: MutableList<RentListing> = mutableListOf()

    inner class RentListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define and initialize views within the ViewHolder
        // For example:
         val title: TextView = itemView.findViewById(R.id.txt_title)
         val city: TextView = itemView.findViewById(R.id.txt_city)
         val rooms: TextView = itemView.findViewById(R.id.txt_rooms)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentListingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rent_listing, parent, false)
        return RentListingViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RentListingViewHolder, position: Int) {
        val rentListing = rentListings[position]

        // Bind the data to the views within the ViewHolder
        // For example:
         holder.title.text = rentListing.title
         holder.city.text = rentListing.neighborhood
        holder.rooms.text = rentListing.floor
    }

    override fun getItemCount(): Int {
        return rentListings.size
    }

    fun addRentListing(rentListing: RentListing) {
        rentListings.add(rentListing)
        notifyDataSetChanged()
    }
}
