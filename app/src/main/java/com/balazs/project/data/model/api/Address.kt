package com.balazs.project.data.model.api

data class Address(
    val city: String,
    val coordinate: Coordinate,
    val country: String,
    val line: String,
    val postal_code: String,
    val state: String,
    val state_code: String,
    val street_direction: String,
    val street_name: String,
    val street_number: String,
    val street_post_direction: Any,
    val street_suffix: String,
    val unit: Any
)