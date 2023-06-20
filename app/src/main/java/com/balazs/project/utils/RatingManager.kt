package com.balazs.project.utils

import android.content.Context

object RatingManager {
    private const val PREFS_NAME = "ItemRatings"
    private const val BIG_AVERAGE_KEY = "BigAverage"

    fun saveRatingData(context: Context, itemId: String, averageRating: Float, reviewCount: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("$itemId-averageRating", averageRating)
        editor.putInt("$itemId-reviewCount", reviewCount)
        editor.apply()
    }

    fun getBigAverage(context: Context): Float {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(BIG_AVERAGE_KEY, 0.0f)
    }
}