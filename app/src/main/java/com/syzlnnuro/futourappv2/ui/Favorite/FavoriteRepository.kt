package com.syzlnnuro.futourappv2.ui.Favorite

class FavoriteRepository(private val favoriteDao: FavoriteDao) {
    fun getAllFavorites() = favoriteDao.getAllFavorites()
    suspend fun addFavorite(place: Favorite) {
        favoriteDao.insert(place)
    }
    suspend fun removeFavoriteById(id: String) {
        favoriteDao.deleteFavoriteById(id)
    }


}