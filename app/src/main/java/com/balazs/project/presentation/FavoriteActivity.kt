package com.balazs.project.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.Favorite
import com.balazs.project.utils.FavoriteAdapter
import com.balazs.project.utils.FavoriteItemsManager
import com.balazs.project.utils.TransparentStatusBarHandler

class FavoriteActivity : AppCompatActivity() {
    private lateinit var favoriteRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        TransparentStatusBarHandler.initTransparentStatusBar(window)
        val btn_back : ImageButton = findViewById(R.id.btn_back_messages)
        btn_back.setOnClickListener {
            finish()
        }
        // Retrieve the favorite items from SharedPreferences using the FavoriteItemsManager
        val favoriteItems = FavoriteItemsManager.getFavoriteItems(this)

// Create an adapter and pass the favorite items to it
        val adapter = FavoriteAdapter(favoriteItems)
        favoriteRecyclerView = findViewById(R.id.rv_favorite)
        favoriteRecyclerView.setHasFixedSize(true)
       favoriteRecyclerView.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
// Set the adapter on the RecyclerView to display the favorite items
        favoriteRecyclerView.adapter = adapter
    }
}