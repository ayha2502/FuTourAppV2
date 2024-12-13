package com.syzlnnuro.futourappv2.ui.Favorite

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getAllFavorites() = favoriteDao.getAllFavorites()
    suspend fun addFavorite(place: Favorite) {
        favoriteDao.insert(place)
    }
    suspend fun removeFavoriteById(id: String) {
        favoriteDao.deleteFavoriteById(id)
    }
    suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            favoriteDao.getFavoriteById(id.toString()).value != null
        }
    }





}