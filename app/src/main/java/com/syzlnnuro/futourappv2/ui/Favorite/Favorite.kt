package com.syzlnnuro.futourappv2.ui.Favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "place_table")
data class Favorite(
    @PrimaryKey
    @field:SerializedName("images")
    val images: List<String?>? = null,

    @field:SerializedName("genre")
    val genre: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("rating")
    val rating: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: String? = null // Ubah ke String
)