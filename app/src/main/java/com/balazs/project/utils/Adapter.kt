import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.api.PropertyItem
import com.balazs.project.data.model.api.PropertyResponse
import com.balazs.project.data.model.api.Result
import com.balazs.project.data.model.rv.DataTenant
import com.balazs.project.data.model.rv.LandlordListing
import com.balazs.project.data.model.rv.RentListing
import com.balazs.project.presentation.RentDetailActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson

class Adapter(private val propertyListings: List<Result>) :
    RecyclerView.Adapter<Adapter.ImageViewHolder>() {
    //private val usaListings: MutableList<Result> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = propertyListings[position]
        Log.d("RecyclerView", "Item position: $position, Photo count: ${item.photos.size}")

        holder.txt_rating.text = item.list_price_min.toString()
        holder.txt_title.text = item.location.address.street_name
        holder.txt_city.text = item.location.address.city
        Log.d("recycler", "${holder.txt_city.text.toString()}")
        Log.d("recycler", "${holder.txt_title.text.toString()}")
        val photoUrl = item.photos.getOrNull(0)?.href
        val descriptiveText = buildString {
            // Check if the property has a name
            item.description.name?.let { name ->
                append("Welcome to $name!\n")
            }

            // Describe the number of bedrooms
            item.description.beds_min?.let { minBeds ->
                item.description.beds_max?.let { maxBeds ->
                    if (minBeds == maxBeds) {
                        append("This property has $minBeds bedroom. ")
                    } else {
                        append("This property has $minBeds - $maxBeds bedrooms. ")
                    }
                }
            }

            // Describe the number of bathrooms
            item.description.baths_min?.let { minBaths ->
                item.description.baths_max?.let { maxBaths ->
                    if (minBaths == maxBaths) {
                        append("It features $minBaths bathroom. ")
                    } else {
                        append("It features $minBaths - $maxBaths bathrooms. ")
                    }
                }
            }

            // Describe the type of property
            item.description.type?.let { type ->
                append("This is an $type. and ")
            }

            // Describe the year it was built
            item.description.year_built?.let { yearBuilt ->
                append("it was built in $yearBuilt.\n")
            }

            // Describe the garage availability
            item.description.garage?.let { garage ->
                append("It comes with a $garage garage.\n")
            }

            // Describe the lot square footage
            item.description.lot_sqft?.let { lotSqft ->
                append("The property sits on a $lotSqft sqft lot.\n")
            }
        }


        holder.itemView.setOnClickListener {

            val intent = Intent(holder.itemView.context, RentDetailActivity::class.java)
            val longitude = item.location.address.coordinate.lon.toString()
            val latitude = item.location.address.coordinate.lat.toString()
            val description = item.description.toString()
            val phoneNumbers: List<String>? = item.advertisers.getOrNull(1)?.office?.phones
                ?.filterNotNull()
                ?.map { it.number }

            val email = item.advertisers.getOrNull(0)?.office?.lead_email?.to

            Log.d("newww", "${phoneNumbers}")
            Log.d("newww", "${email}")




            intent.putExtra("phone", phoneNumbers?.get(1))
            intent.putExtra("email", email)
            intent.putExtra("description", descriptiveText)
            intent.putExtra("title", holder.txt_title.text) // pass any data to the next activity
            intent.putExtra("city", holder.txt_city.text) // pass any data to the next activity
            intent.putExtra("price", holder.txt_rating.text) // pass any data to the next activity
            intent.putExtra("lon", longitude) // pass any data to the next activity
            intent.putExtra("lat", latitude) // pass any data to the next activity
            //intent.putExtra("price", holder.price.text) // pass any data to the next activity
            //intent.putExtra("city", holder.title.text) // pass any data to the next activity
            //intent.putExtra("rooms", holder.title.text) // pass any data to the next activity
            intent.putExtra("photoUrl", photoUrl)
            val photosUrls = item.photos.mapNotNull { it.href } // Extract the photo URLs
            Log.d("idka", "${photosUrls}")
            intent.putStringArrayListExtra("photosUrls", ArrayList(photosUrls))
            holder.itemView.context.startActivity(intent)


            //Log.d("Adapter", "Photo URL: $photoUrl")


        }


        // Load the photo using a library like Glide or Picasso
        Glide.with(holder.itemView.context)
            .load(photoUrl)
            .into(holder.iv_photo)

        //holder.iv_photo.setImageResource(R.drawable.profile)
        holder.iv_city.setImageResource(R.drawable.ic_city)
        holder.iv_star.setImageResource(R.drawable.ic_money)


        /*  holder.itemView.setOnClickListener {
              val intent = Intent(holder.itemView.context, RentDetailActivity::class.java)
              //intent.putExtra("item_id", currentItem.id) // pass any data to the next activity
              holder.itemView.context.startActivity(intent)
          }*/



    }

    override fun getItemCount(): Int {
        return propertyListings.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_photo: ImageView = itemView.findViewById(R.id.iv_mock)
        val iv_city: ImageView = itemView.findViewById(R.id.iv_city)
        val iv_star: ImageView = itemView.findViewById(R.id.iv_star)
        val txt_title: TextView = itemView.findViewById(R.id.txt_title)
        val txt_city: TextView = itemView.findViewById(R.id.txt_city)
        val txt_rating: TextView = itemView.findViewById(R.id.txt_rating)

    }


    fun saveUsaListingsToSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefsUSA", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Convert the list of listings to JSON string
        val listingsJson = Gson().toJson(propertyListings)

        // Save the JSON string in SharedPreferences
        editor.putString("usa_listings", listingsJson)
        editor.apply()
    }
    /*fun saveUsaListingsToSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences("MyPrefs2USA", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = gson.toJson(propertyListings)
        sharedPreferences.edit().putString("usaListings", json).apply()
    }*/
    /*  fun setUsaListings(usaListings: List<Result>) {
          this.propertyListings.clear()
          this.propertyListings.addAll(usaListings)
          //filter("") // reapply the filter after setting the new list
      }*/
    private fun parseAndGenerateDescription(description: String?): String {
        // Parse the JSON description and generate a descriptive text
        // Customize this logic based on the structure and fields in your JSON

        return "Generated descriptive text"
    }

}
