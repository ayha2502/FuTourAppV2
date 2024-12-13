package com.syzlnnuro.futourappv2.ui.Favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Favorite)

    @Query("SELECT * FROM place_table WHERE id = :id")
    fun getEventById(id: Int): LiveData<Favorite?>

    @Query("SELECT * FROM place_table")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("DELETE FROM place_table")
    suspend fun deleteAll()

    @Query("DELETE FROM place_table WHERE id = :placeId")
    suspend fun deleteFavoriteById(eventId: String?)
}