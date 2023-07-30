package com.balazs.project.data.model

data class RatingData(

    val punctualityRating: Float,
    val kindnessRating: Float,
    val pricePerWorkRating: Float,
    val professionalismRating: Float
){
    fun calculateAverage(): Float {
        val totalRatings = 4 // Number of rating categories
        val sum = punctualityRating + kindnessRating + pricePerWorkRating + professionalismRating
        return sum / totalRatings
    }
}
