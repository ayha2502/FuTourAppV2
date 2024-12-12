package com.syzlnnuro.futourappv2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.data.ListofPlaceResponse
import com.syzlnnuro.futourappv2.databinding.ActivityDetailPlaceBinding
import com.syzlnnuro.futourappv2.searchData.RecommendationItem

class DetailPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        // Tambahkan aksi jika diperlukan, seperti tombol favorit
        binding.favoriteButton.setOnClickListener {
            // Logika untuk menambahkan ke favorit
        }

        // Tombol kembali
//        binding.backButton.setOnClickListener {
//            onBackPressed()
//        }
    }
}
