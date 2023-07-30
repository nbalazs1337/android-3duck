package com.balazs.project.data.model.api

data class HomeSearch(
    val count: Int,
    val results: List<Result>,
    val total: Int
)