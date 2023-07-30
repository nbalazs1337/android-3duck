package com.balazs.project.data.model.api

data class GeoX(
    val city: String,
    val geo_statistics: GeoStatisticsX,
    val geo_type: String,
    val slug_id: String,
    val state_code: String
)