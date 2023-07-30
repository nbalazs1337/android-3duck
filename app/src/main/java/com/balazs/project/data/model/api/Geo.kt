package com.balazs.project.data.model.api

data class Geo(
    val geo_statistics: GeoStatistics,
    val parents: List<Parent>,
    val recommended_cities: RecommendedCities,
    val recommended_counties: RecommendedCounties,
    val recommended_neighborhoods: RecommendedNeighborhoods,
    val recommended_zips: RecommendedZips
)