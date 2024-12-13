package com.syzlnnuro.futourappv2.ui.Favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface FavoriteDao {

    // Menyisipkan data Favorite

    @Query("SELECT * FROM place_table WHERE id = :id")
    suspend fun getFavoriteByIdSuspend(id: String): Favorite?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Favorite)

    // Mengambil Favorite berdasarkan ID
    @Query("SELECT * FROM place_table WHERE id = :id")
    fun getFavoriteById(id: String): LiveData<Favorite>

    // Mengambil semua Favorite
    @Query("SELECT * FROM place_table")
    fun getAllFavorites(): LiveData<List<Favorite>>

    // Menghapus semua Favorite
    @Query("DELETE FROM place_table")
    suspend fun deleteAll(): Int // Mengembalikan jumlah baris yang terhapus

    // Menghapus Favorite berdasarkan ID
    @Query("DELETE FROM place_table WHERE id = :id")
    suspend fun deleteFavoriteById(id: String): Int // Mengembalikan jumlah baris yang terhapus
}
