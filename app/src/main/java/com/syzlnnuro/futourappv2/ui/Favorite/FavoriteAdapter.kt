package com.syzlnnuro.futourappv2.ui.Favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.R

class FavoriteAdapter(
    private var places: List<Favorite> = emptyList(),
    private val onFavoriteClick: (Favorite) -> Unit // Callback untuk menghapus item
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val namePlace: TextView = view.findViewById(R.id.item_title)
        val category: TextView = view.findViewById(R.id.item_category)
        val imagePlace: ImageView = view.findViewById(R.id.item_image)
        val descPlace: TextView = view.findViewById(R.id.item_description)
//        val deleteButton: ImageView = view.findViewById(R.id.ivDelete) // Ikon hapus favorit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_result, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val place = places[position]
        holder.namePlace.text = place.name
        holder.category.text = place.genre
        holder.descPlace.text = place.description

        Glide.with(holder.itemView.context)
            .load(place.images)
            .into(holder.imagePlace)


    }

    override fun getItemCount(): Int = places.size

    fun submitList(newPlaces: List<Favorite>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}
