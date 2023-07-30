package com.balazs.project.utils

import android.content.Context
import android.preference.PreferenceManager
import com.balazs.project.data.model.rv.Favorite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
object FavoriteItemsManager {
private const val FAVORITE_ITEMS_KEY = "favorite_items"

fun saveFavoriteItem(context: Context, favoriteItem: List<Favorite>) {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val editor = sharedPreferences.edit()



    // Convert the list of favorite items to a JSON string
    val json = Gson().toJson(favoriteItem)

    // Save the JSON string in SharedPreferences
    editor.putString(FAVORITE_ITEMS_KEY, json)
    editor.apply()
}

fun getFavoriteItems(context: Context): MutableList<Favorite> {
    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val json = sharedPreferences.getString(FAVORITE_ITEMS_KEY, null)

    return if (json != null) {
        // Convert the JSON string to a list of favorite items
        Gson().fromJson(json, object : TypeToken<MutableList<Favorite>>() {}.type)
    } else {
        // Return an empty list if no favorite items are stored
        mutableListOf()
    }
}
    fun deleteFavoriteItem(context: Context, position: Int) {
        val favoriteItems = getFavoriteItems(context)
        favoriteItems.removeAt(position)
        saveFavoriteItem(context, favoriteItems)
    }
}