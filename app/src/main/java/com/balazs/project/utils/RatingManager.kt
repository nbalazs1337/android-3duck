import android.content.Context

object RatingManager {
    private const val PREFS_NAME = "ItemRatings"

    fun saveRatingData(context: Context, itemId: String, averageRating: Float, reviewCount: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("$itemId-averageRating", averageRating)
        editor.putInt("$itemId-reviewCount", reviewCount)
        editor.apply()
    }

    fun getAverageRating(context: Context, itemId: String): Float {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("$itemId-averageRating", 0.0f)
    }

    fun getReviewCount(context: Context, itemId: String): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt("$itemId-reviewCount", 0)
    }
}
