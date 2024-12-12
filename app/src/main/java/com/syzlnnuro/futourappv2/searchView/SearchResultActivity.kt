package com.syzlnnuro.futourappv2.searchView

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syzlnnuro.futourappv2.DetailPlaceActivity
import com.syzlnnuro.futourappv2.databinding.ActivitySearchResultBinding
import com.syzlnnuro.futourappv2.searchAdapter.SearchAdapter
import com.syzlnnuro.futourappv2.searchData.RecommendationItem

data class SearchResponseItem(
    val name: String,
    val genre: String,
    val description: String,
    val images: List<String>?
)

class SearchResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchResultBinding
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recommendations = intent.getParcelableArrayListExtra<RecommendationItem>("recommendations") ?: arrayListOf()

        setupRecyclerView(recommendations)
    }

    private fun setupRecyclerView(recommendations: List<RecommendationItem>) {
        searchAdapter = SearchAdapter(recommendations) { recommendation ->
            // Navigasi ke DetailActivity saat item diklik
            val intent = Intent(this, DetailPlaceActivity::class.java).apply {
                putExtra("recommendation", recommendation)
            }
            startActivity(intent)
        }

        binding.rvSearchResult.apply {
            layoutManager = LinearLayoutManager(this@SearchResultActivity)
            adapter = searchAdapter
        }
    }
}
