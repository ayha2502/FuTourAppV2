package com.syzlnnuro.futourappv2.searchAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.databinding.ItemCardResultBinding
import com.syzlnnuro.futourappv2.searchData.RecommendationItem

class SearchAdapter(
    private var recommendations: List<RecommendationItem>,
    private val onItemClicked: (RecommendationItem) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(private val binding: ItemCardResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(recommendation: RecommendationItem, onItemClicked: (RecommendationItem) -> Unit) {
            binding.itemTitle.text = recommendation.name
            binding.itemCategory.text = recommendation.genre
            binding.itemDescription.text = recommendation.description

            recommendation.images?.firstOrNull()?.let { imageUrl ->
                Glide.with(binding.itemImage.context)
                    .load(imageUrl)
                    .into(binding.itemImage)
            }

            // Set klik listener untuk item
            binding.root.setOnClickListener {
                onItemClicked(recommendation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemCardResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val recommendation = recommendations[position]
        holder.bind(recommendation, onItemClicked)
    }

    override fun getItemCount(): Int = recommendations.size

    fun updateRecommendations(newRecommendations: List<RecommendationItem>) {
        recommendations = newRecommendations
        notifyDataSetChanged()
    }
}
