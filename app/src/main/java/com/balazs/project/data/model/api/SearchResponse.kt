package com.balazs.project.data.model.api

import com.balazs.project.data.model.api.Element

data class SearchResponse(
    val actualPage: Int,
    val elementList: List<Element>,
    val itemsPerPage: Int,
    val lowerRangePosition: Int,
    val numPaginations: Int,
    val paginable: Boolean,
    val summary: List<String>,
    val total: Int,
    val totalPages: Int,
    val upperRangePosition: Int
)