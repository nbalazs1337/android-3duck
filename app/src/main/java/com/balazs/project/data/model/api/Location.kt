package com.balazs.project.data.model.api

data class Location(
    val address: Address,
    val county: County,
    val neighborhoods: List<Neighborhood>,
    val search_areas: List<SearchArea>
)