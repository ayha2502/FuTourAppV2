package com.syzlnnuro.futourappv2

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable


@Parcelize
data class PlaceResponse(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null
): Parcelable
