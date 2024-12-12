package com.syzlnnuro.futourappv2.searchData

import android.os.Parcel
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class SearchResponse(

	@field:SerializedName("SearchResponse")
	val searchResponse: List<SearchResponseItem?>? = listOf()
)

data class SearchResponseItem(
	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("similarity")
	val similarity: Any? = null,

	@field:SerializedName("genre")
	val genre: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: Int? = null,

	@field:SerializedName("description")
	val description: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.createStringArrayList(),
		parcel.readValue(Any::class.java.classLoader),
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readString()
	)

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeStringList(images)
		parcel.writeValue(similarity)
		parcel.writeString(genre)
		parcel.writeString(name)
		parcel.writeValue(rating)
		parcel.writeString(description)
	}

	override fun describeContents(): Int = 0

	companion object CREATOR : Parcelable.Creator<SearchResponseItem> {
		override fun createFromParcel(parcel: Parcel): SearchResponseItem {
			return SearchResponseItem(parcel)
		}

		override fun newArray(size: Int): Array<SearchResponseItem?> {
			return arrayOfNulls(size)
		}
	}
}