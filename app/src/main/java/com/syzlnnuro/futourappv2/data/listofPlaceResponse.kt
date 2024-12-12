package com.syzlnnuro.futourappv2.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class ListofPlaceResponse(

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
) : Parcelable
