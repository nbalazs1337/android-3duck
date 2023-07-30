package com.balazs.project.utils

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.balazs.project.R
import com.balazs.project.data.model.rv.Favorite


class FavoriteAdapter(private val favoriteItems: MutableList<Favorite>) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteItem = favoriteItems[position]

        holder.titleTextView.text = favoriteItem.title
        holder.cityTextView.text = favoriteItem.city

        if (favoriteItem.photoUrl != null) {
            Glide.with(holder.itemView.context)
                .load(favoriteItem.photoUrl)
                .into(holder.photoImageView)
        } else if (favoriteItem.photoUri != null) {
            val photoUri = Uri.parse(favoriteItem.photoUri)
            holder.photoImageView.setImageURI(photoUri)
        }

        holder.button.setOnClickListener {

            FavoriteItemsManager.deleteFavoriteItem(holder.itemView.context, position)

            // Remove item from dataset
            favoriteItems.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoriteItems.size)
        }
    }



    override fun getItemCount(): Int {
        return favoriteItems.size
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.iv_favorite)
        val titleTextView: TextView = itemView.findViewById(R.id.txt_title_favorite)
        val cityTextView: TextView = itemView.findViewById(R.id.txt_city_favorite)
        val button : ImageButton = itemView.findViewById(R.id.btn_delete_favorite)
    }
}
