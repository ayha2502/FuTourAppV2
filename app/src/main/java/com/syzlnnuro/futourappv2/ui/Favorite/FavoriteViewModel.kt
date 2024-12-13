package com.syzlnnuro.futourappv2.ui.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDao: FavoriteDao
    private val repository: FavoriteRepository

    init {
        // Pastikan AppDatabase sudah diimport dan tersedia
        val database = AppDatabase.getDatabase(application)
        favoriteDao = database.favoriteDao()
        repository = FavoriteRepository(favoriteDao)
    }
    // LiveData untuk semua data favorit
    val favorites: LiveData<List<Favorite>> = repository.getAllFavorites()

    // Tambah tempat ke favorit
    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            repository.addFavorite(favorite)
        }
    }

    // Hapus tempat dari favorit
    fun removeFavorite(favoriteId: String) {
        viewModelScope.launch {
            repository.removeFavoriteById(favoriteId)
        }
    }
    suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            repository.isFavorite(id)
        }
    }
}
