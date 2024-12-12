package com.syzlnnuro.futourappv2.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.databinding.ItemCardResultBinding
import com.syzlnnuro.futourappv2.searchData.RecommendationItem

class SearchAdapter(private var recommendations: List<RecommendationItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    // ViewHolder untuk setiap item dalam RecyclerView
    class SearchViewHolder(private val binding: ItemCardResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: RecommendationItem) {
            binding.itemTitle.text = recommendation.name
            binding.itemCategory.text = recommendation.genre
            binding.itemDescription.text = recommendation.description

            // Misalnya, jika ada gambar untuk ditampilkan, kita bisa menambahkannya di sini
            recommendation.images?.firstOrNull()?.let { imageUrl ->
                // Menggunakan Glide untuk menampilkan gambar
                Glide.with(binding.itemImage.context)
                    .load(imageUrl)
                    .into(binding.itemImage)
            }
        }
    }

    // Fungsi untuk membuat ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemCardResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    // Fungsi untuk mengikat data ke item View
    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.bind(recommendation)
    }

    // Mengembalikan jumlah item dalam data
    override fun getItemCount(): Int = recommendations.size

    // Fungsi untuk memperbarui data di adapter
    fun updateRecommendations(newRecommendations: List<RecommendationItem>) {
        recommendations = newRecommendations
        notifyDataSetChanged()
    }
}
