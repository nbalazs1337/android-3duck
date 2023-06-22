package com.balazs.project.data.model.rv

data class LandlordListing(
    val itemId: String,
    val name: String,
    val service: String,
    val price: String,
    //val phoneNumb: String,
    val experience: String,
    val phoneNumber: String,
    var rating: Float = 0.0f
)
