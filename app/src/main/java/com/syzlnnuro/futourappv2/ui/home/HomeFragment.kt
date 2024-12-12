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
import com.google.android.gms.common.api.Api
import com.syzlnnuro.futourappv2.ListPlaceAdapter
import com.syzlnnuro.futourappv2.ResultActivity
import com.syzlnnuro.futourappv2.databinding.FragmentHomeBinding
import com.syzlnnuro.futourappv2.searchApi.SearchApiConfig
import com.syzlnnuro.futourappv2.searchData.SearchRequest
import com.syzlnnuro.futourappv2.searchData.SearchResponse
import com.syzlnnuro.futourappv2.searchView.SearchResultActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListPlaceAdapter

    private lateinit var recommendationsAdapter: ListPlaceAdapter
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var categoriesAdapter: ListPlaceAdapter
    private lateinit var recommendedAdapter: ListPlaceAdapter
    private lateinit var bestAdapter: ListPlaceAdapter

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

        // Tampilkan loading indicator
        binding.progressBarHome.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val searchApiConfig = SearchApiConfig()
                val searchApiService = searchApiConfig.getSearchApiService("your_token_here") // Ganti "your_token_here" dengan token yang valid
                val response: SearchResponse = searchApiService.search(request)
                handleSearchResponse(response)
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(requireContext(), "Search failed: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                // Sembunyikan loading indicator
                binding.progressBarHome.visibility = View.GONE
            }
        }
    }

    private fun handleSearchResponse(response: SearchResponse) {
        // Menampilkan genre yang diprediksi
        //binding.tvCategories.text = "Predicted Genre: ${response.predictedGenre ?: "-"}"

        // Ambil rekomendasi
        val recommendations = response.searchResponse?.filterNotNull() ?: emptyList()

        // Kirimkan data ke ResultActivity
        val intent = Intent(requireContext(), SearchResultActivity::class.java)
        intent.putParcelableArrayListExtra("recommendations", ArrayList(recommendations))
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
        // Observe Categories data
        homeViewModel.categories.observe(viewLifecycleOwner) { categories ->
            categories?.let {
                categoriesAdapter.updatePlaces(it)  // Update the categories adapter
            }
        }

        // Observe Recommended Locations data
        homeViewModel.recommendedLocations.observe(viewLifecycleOwner) { recommended ->
            recommended?.let {
                recommendedAdapter.updatePlaces(it)  // Update the recommended adapter
            }
        }

        // Observe Best Locations data
        homeViewModel.bestLocations.observe(viewLifecycleOwner) { best ->
            best?.let {
                bestAdapter.updatePlaces(it)  // Update the best adapter
            }
        }

        // Observe loading state
        homeViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe error messages
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
