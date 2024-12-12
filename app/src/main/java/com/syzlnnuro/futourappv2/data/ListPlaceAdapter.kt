package com.syzlnnuro.futourappv2.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.DetailPlaceActivity
import com.syzlnnuro.futourappv2.R

class ListPlaceAdapter (private var places: List<ListofPlaceResponse> = emptyList()):
    RecyclerView.Adapter<ListPlaceAdapter.placeViewHolder>(){

    fun updatePlaces(newPlaces: List<ListofPlaceResponse>) {
        places = newPlaces
        notifyDataSetChanged()
    }
        inner class placeViewHolder (view: View) : RecyclerView.ViewHolder(view){
            val kategory : TextView = view.findViewById(R.id.tvCategory)
            val namePlace : TextView = view.findViewById(R.id.tvLocationName)
            val descriptPlace : TextView = view.findViewById(R.id.tvDescription)
            val imagePlace : ImageView = view.findViewById(R.id.ivLocationImage)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): placeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return placeViewHolder(view)
    }

    override fun onBindViewHolder(holder: placeViewHolder, position: Int) {
        val place = places[position]
        holder.namePlace.text = place.name ?: "Nama tidak tersedia"
        holder.kategory.text = place.genre ?: "Kategory tidak tersedia"
        holder.descriptPlace.text = place.description ?: "Deskripsi tidak tersedia"
        val imageUrl = place.images?.firstOrNull() ?: "" // Ambil gambar pertama atau kosong
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.imagePlace)


        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailPlaceActivity::class.java)
            intent.putExtra("place", place)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = places.size
}