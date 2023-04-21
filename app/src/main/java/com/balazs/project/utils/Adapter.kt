import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.Data

class Adapter(private val images: List<Data>) :
    RecyclerView.Adapter<Adapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        holder.iv_photo.setImageResource(R.drawable.mock)
        holder.txt_title.text = "Garsoniera Manastur"
        holder.txt_city.text = "Cluj-Napoca"
        holder.txt_title.text = "4.3"
        holder.iv_city.setImageResource(R.drawable.ic_city)
        holder.iv_star.setImageResource(R.drawable.ic_star)
    }

    override fun getItemCount(): Int {
        return images.size
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
