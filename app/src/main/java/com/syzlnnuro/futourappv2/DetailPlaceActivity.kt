package com.syzlnnuro.futourappv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import com.syzlnnuro.futourappv2.databinding.ActivityDetailPlaceBinding
import com.syzlnnuro.futourappv2.searchData.RecommendationItem
import com.syzlnnuro.futourappv2.ui.Favorite.Favorite
import com.syzlnnuro.futourappv2.ui.Favorite.FavoriteViewModel
import kotlinx.coroutines.launch

class DetailPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi ViewModel
//        favoriteViewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[FavoriteViewModel::class.java]



        // Mendapatkan data yang dikirim dari SearchResultActivity
        val recommendation = intent.getParcelableExtra<RecommendationItem>("recommendation")
        // Mendapatkan data yang dikirim dari HomeFragment melalui ListPlaceAdapter
        val place = intent.getParcelableExtra<ListofPlaceResponse>("place")

        // Menampilkan data di tampilan detail
        recommendation?.let {
            binding.titleTextView.text = it.name
            binding.categoryTextView.text = it.genre
            binding.descriptionTextView.text = it.description

            // Menampilkan gambar menggunakan Glide jika tersedia
            it.images?.firstOrNull()?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.imageView)
            }
        }
        // Menampilkan data di tampilan detail
        place?.let {
            binding.titleTextView.text = it.name
            binding.categoryTextView.text = it.genre
            binding.descriptionTextView.text = it.description

            // Menampilkan gambar menggunakan Glide jika tersedia
            it.images?.firstOrNull()?.let { imageUrl ->
                Glide.with(this)
                    .load(imageUrl)
                    .into(binding.imageView)
            }
        }
//        lifecycleScope.launch {
//            place?.let { placeData ->
//                val isFavorite = favoriteViewModel.isFavorite(placeData.id?.toIntOrNull() ?: 0)
//
//                if (isFavorite) {
//                    binding.favoriteButton.setImageResource(R.drawable.ic_favorite_filled) // Icon merah
//                } else {
//                    binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border) // Icon default
//                }
//            }
//        }

        // Logika tombol favorite
//        binding.favoriteButton.setOnClickListener {
//            place?.let { placeData ->
//                lifecycleScope.launch {
//                    val isFavorite = favoriteViewModel.isFavorite(placeData.id?.toIntOrNull() ?: 0)
//                    if (isFavorite) {
//                        // Jika sudah di favorit, hapus
//                        favoriteViewModel.removeFavorite(placeData.id ?: "")
//                        Toast.makeText(this@DetailPlaceActivity, "Removed from Favorite", Toast.LENGTH_SHORT).show()
//                        binding.favoriteButton.setImageResource(R.drawable.ic_favorite_border) // Icon default
//                    } else {
//                        // Jika belum di favorit, tambahkan
//                        val favorite = Favorite(
//                            id = placeData.id ?: "",
//                            name = placeData.name ?: "Nama tidak tersedia",
//                            images = placeData.images?.firstOrNull(),
//                            genre = placeData.genre,
//                            description = placeData.description,
//                            rating = placeData.rating
//                        )
//                        favoriteViewModel.addFavorite(favorite)
//                        Toast.makeText(this@DetailPlaceActivity, "Added to Favorite", Toast.LENGTH_SHORT).show()
//                        binding.favoriteButton.setImageResource(R.drawable.ic_favorite_filled) // Icon merah
//                    }
//                }
//            }
//        }
    }
}
