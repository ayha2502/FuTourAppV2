package com.syzlnnuro.futourappv2.ui.Profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.syzlnnuro.futourappv2.LoginActivity
import com.syzlnnuro.futourappv2.R
import com.syzlnnuro.futourappv2.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        setupUI()
        setupObservers()

        return binding.root
    }

    private fun setupUI() {
        binding.imageProfil.setOnClickListener {
            selectImageFromGallery()
        }

        binding.editProfile.setOnClickListener {
            // Tambahkan logika untuk membuka layar edit profil
        }

        binding.editPassword.setOnClickListener {
            // Tambahkan logika untuk membuka layar edit password
        }

        binding.logout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun setupObservers() {
        viewModel.username.observe(viewLifecycleOwner, Observer {
            binding.namaProfil.text = it
        })

        viewModel.email.observe(viewLifecycleOwner, Observer {
            binding.emailProfil.text = it
        })

        viewModel.profilePictureUrl.observe(viewLifecycleOwner, Observer { url ->
            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.avatar) // Placeholder jika gambar belum ada
                .into(binding.imageProfil)
        })
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001 && resultCode == android.app.Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let { viewModel.uploadProfilePicture(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
