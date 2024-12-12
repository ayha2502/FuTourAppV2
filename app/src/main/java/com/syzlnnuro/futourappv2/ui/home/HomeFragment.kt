package com.syzlnnuro.futourappv2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syzlnnuro.futourappv2.data.ListPlaceAdapter
import com.syzlnnuro.futourappv2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListPlaceAdapter

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
