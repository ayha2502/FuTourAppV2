package com.syzlnnuro.futourappv2.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syzlnnuro.futourappv2.data.ListPlaceAdapter
import com.syzlnnuro.futourappv2.databinding.FragmentHomeBinding
import com.syzlnnuro.futourappv2.searchApi.SearchApiConfig
import com.syzlnnuro.futourappv2.searchData.RecommendationItem
import com.syzlnnuro.futourappv2.searchData.SearchRequest
import com.syzlnnuro.futourappv2.searchView.SearchResultActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoriesAdapter: ListPlaceAdapter
    private lateinit var recommendedAdapter: ListPlaceAdapter
    private lateinit var bestAdapter: ListPlaceAdapter
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViews()
        observeViewModel()
        homeViewModel.fetchPlaces()

        setupSearchFeature()
    }

    private fun setupSearchFeature() {
        binding.searchButton.setOnClickListener {
            val query = binding.searchInput.text.toString().trim()
            if (query.isNotEmpty()) {
                performSearch(query)
            } else {
                Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSearch(description: String) {
        val request = SearchRequest(description = description)

        binding.progressBarHome.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val searchApiService = SearchApiConfig().getSearchApiService("your_token_here")
                val response = searchApiService.search(request)
                val searchResults = response.recommendation?.filterNotNull() ?: emptyList()
                handleSearchResponse(searchResults)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Search failed: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBarHome.visibility = View.GONE
            }
        }
    }

    private fun handleSearchResponse(response: List<RecommendationItem>) {
        val intent = Intent(requireContext(), SearchResultActivity::class.java)
        intent.putParcelableArrayListExtra("recommendations", ArrayList(response))
        startActivity(intent)
    }

    private fun setupRecyclerViews() {
        categoriesAdapter = ListPlaceAdapter(emptyList())
        recommendedAdapter = ListPlaceAdapter(emptyList())
        bestAdapter = ListPlaceAdapter(emptyList())

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }

        binding.rvRecommendedLocations.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recommendedAdapter
        }

        binding.rvBestLocations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestAdapter
        }
    }

    private fun observeViewModel() {
        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoriesAdapter.updatePlaces(it)
            }
        }

        homeViewModel.recommendedLocations.observe(viewLifecycleOwner) { recommended ->
            recommended?.let {
                recommendedAdapter.updatePlaces(it)
            }
        }

        homeViewModel.bestLocations.observe(viewLifecycleOwner) { best ->
            best?.let {
                bestAdapter.updatePlaces(it)
            }
        }

        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        homeViewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
