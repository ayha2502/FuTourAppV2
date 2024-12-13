package com.syzlnnuro.futourappv2.ui.Favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "place_table")
data class Favorite(
    @PrimaryKey
    val id: String,
    val name: String,
    val images: String?, // Simpan satu URL gambar saja
    val genre: String?,
    val description: String?,
    val rating: Int?
)
