package com.balazs.project.presentation

import ImageAdapter
import android.content.Context
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.balazs.project.R
import com.balazs.project.data.model.api.Photo
import com.balazs.project.data.model.api.Result
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
        Log.d("rent", "${photoUrl}")
        val image: ImageView = findViewById(R.id.iv_background)
        // Use the retrieved data in your activity
        Glide.with(this)
            .load(photoUrl)
            .into(image)







        // Initialize the MapView
        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        //image list recyclerview
        val recyclerViewImages: RecyclerView = findViewById(R.id.rv_photo)
        val imageUrls = intent.getStringArrayListExtra("photosUrls")
        Log.d("idk", "${imageUrls}")
        recyclerViewImages.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = imageUrls?.let { ImageAdapter(it) }
        recyclerViewImages.adapter = adapter

        //displayRentDetails()

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