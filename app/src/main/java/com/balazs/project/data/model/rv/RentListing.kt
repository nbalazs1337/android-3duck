package com.balazs.project.data.model.rv

data class RentListing(
    val title: String,
    val neighborhood: String,
    val street: String,
    val price: String,
    val description: String,
    var photoUrl: MutableList<String>

)
