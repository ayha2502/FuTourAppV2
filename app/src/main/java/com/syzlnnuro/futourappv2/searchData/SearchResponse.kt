package com.syzlnnuro.futourappv2.searchData

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SearchResponse(
	@field:SerializedName("recommendation")
	val recommendation: List<RecommendationItem>? = null
) : Parcelable


@Parcelize
data class RecommendationItem(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("similarity")
	val similarity: Double? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable
