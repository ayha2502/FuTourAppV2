package com.syzlnnuro.futourappv2.ui.Favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val favoriteDao = AppDatabase.getDatabase(application).favoriteDao()
    private val repository = FavoriteRepository(favoriteDao)

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
}
