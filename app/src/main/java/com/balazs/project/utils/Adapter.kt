import android.content.Intent
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
import com.balazs.project.presentation.RentDetailActivity
import com.bumptech.glide.Glide

class Adapter(private val propertyListings: List<Result>) :
    RecyclerView.Adapter<Adapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = propertyListings[position]

        holder.txt_rating.text = "4.3"
        holder.txt_title.text = item.location.address.postal_code
        holder.txt_city.text = item.location.address.city
        Log.d("recycler", "${holder.txt_city.text.toString()}")
        Log.d("recycler", "${holder.txt_title.text.toString()}")
        // Load the photo using a library like Glide or Picasso
       /* Glide.with(holder.itemView.context)
            .load(item.data.home_search.results[position].photos[position].href)
            .into(holder.iv_photo)*/

        holder.iv_photo.setImageResource(R.drawable.mock)
        holder.iv_city.setImageResource(R.drawable.ic_city)
        holder.iv_star.setImageResource(R.drawable.ic_star)


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
}
