package com.syzlnnuro.futourappv2.ui.Favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.syzlnnuro.futourappv2.data.ListPlaceAdapter
import com.syzlnnuro.futourappv2.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        setupRecyclerView()
        observeData()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = FavoriteAdapter(onFavoriteClick = { favorite ->
            favoriteViewModel.removeFavorite(favorite.id)
            Toast.makeText(requireContext(), "Removed from Favorite", Toast.LENGTH_SHORT).show()
        })

        binding.rvFav.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeData() {
        favoriteViewModel.favorites.observe(viewLifecycleOwner) { places ->
            favoriteAdapter.submitList(places)
            if (places.isEmpty()) {
                binding.rvFav.visibility = View.VISIBLE
            } else {
                binding.rvFav.visibility = View.GONE
            }
        }
    }
}
