package com.balazs.project.presentation

import ImageAdapter
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.rv.Favorite
import com.balazs.project.utils.FavoriteItemsManager
import com.balazs.project.utils.TransparentStatusBarHandler
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException

class RentDetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var isFavorite: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rent_detail)
        TransparentStatusBarHandler.initTransparentStatusBar(window)


        val btn_back: ImageButton = findViewById(R.id.btn_back_rentDetail)
        btn_back.setOnClickListener {
            finish()
        }
        val getTitle = intent.getStringExtra("title")
        val title: TextView = findViewById(R.id.txt_title)
        title.text = getTitle


        val getPrice = intent.getStringExtra("price")
        val price: TextView = findViewById(R.id.txt_price_rent_listing)
        price.text = getPrice + " $"

        val getCity = intent.getStringExtra("city")
        val city: TextView = findViewById(R.id.txt_city_rent)
        city.text = getCity


        val getUsaDescription = intent.getStringExtra("description")

        val sharedPreferences = getSharedPreferences("MyPrefsDescription", Context.MODE_PRIVATE)
        val getDescription = sharedPreferences.getString("description", "")
        val description: TextView = findViewById(R.id.txt_description)
        if (getUsaDescription.isNullOrEmpty()) {
            description.text = getDescription
        } else {
            description.text = getUsaDescription
            Log.d("description", "${getUsaDescription}")
        }

        val photoUrl = intent.getStringExtra("photoUrl")
        val photoUri = intent.getStringExtra("photoUri")


        Log.d("urls", "${photoUrl}")
        Log.d("urls", "${photoUri}")

        val image: ImageView = findViewById(R.id.iv_background)

        if ((photoUrl != null) && photoUrl.isNotEmpty()) {
            Glide.with(this)
                .load(photoUrl)
                .into(image)
        } else if (photoUri != null && photoUri.isNotEmpty()) {
            val goodPhotoUri = Uri.parse(photoUri)
            Log.d("urls", "${goodPhotoUri}")
            image.setImageURI(goodPhotoUri)
        } else {
            // No photo data available, set a default image
            Glide.with(this)
                .load(R.drawable.mock)
                .into(image)
        }









        // Initialize the MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        //image list recyclerview
        val recyclerViewImages: RecyclerView = findViewById(R.id.rv_photo)
        val imageUrls = intent.getStringArrayListExtra("photosUrls")
        Log.d("idk", "${imageUrls}")
        recyclerViewImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = imageUrls?.let { ImageAdapter(it) }
        recyclerViewImages.adapter = adapter

        //displayRentDetails()

        //favorite rent
        val btn_img: ImageButton = findViewById(R.id.btn_favourite)
        btn_img.setOnClickListener {

            isFavorite = !isFavorite
            val heartIcon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            btn_img.setImageResource(heartIcon)
            val photoUrl = intent.getStringExtra("photoUrl")
            val title = intent.getStringExtra("title")
            val city = intent.getStringExtra("city")

            // Create a FavoriteItem object
            val favoriteItems = FavoriteItemsManager.getFavoriteItems(this).toMutableList()
            val newFavoriteItem = Favorite(photoUrl, title, city)
            favoriteItems.add(newFavoriteItem)
            FavoriteItemsManager.saveFavoriteItem(this, favoriteItems)


        }

    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val hasAddress = intent.hasExtra("city") && intent.hasExtra("title")
        val hasCoordinates = intent.hasExtra("latitude") && intent.hasExtra("longitude")

        if (hasAddress) {
            // Case 1: Address provided
            val city = intent.getStringExtra("city")
            val neighborhood = intent.getStringExtra("title")
            val address = "$neighborhood, $city"

            val geocoder = Geocoder(this)

            try {
                val locationList = geocoder.getFromLocationName(address, 1)
                if (locationList != null && locationList.isNotEmpty()) {
                    val location = locationList[0]
                    val latitude = location.latitude
                    val longitude = location.longitude

                    // Move camera to the marker position
                    val markerPosition = LatLng(latitude, longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 12f))

                    // Add marker to the map
                    googleMap.addMarker(
                        MarkerOptions().position(markerPosition).title("Rent Listing Location")
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else if (hasCoordinates) {
            // Case 2: Coordinates provided
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)

            // Move camera to the marker position
            val markerPosition = LatLng(latitude, longitude)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerPosition, 12f))

            // Add marker to the map
            googleMap.addMarker(
                MarkerOptions().position(markerPosition).title("Rent Listing Location")
            )
        }
    }


}