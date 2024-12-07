package com.syzlnnuro.futourappv2.ui.Profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance().getReference("Users")
    private val storage = FirebaseStorage.getInstance().getReference("ProfilePictures")

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _profilePictureUrl = MutableLiveData<String>()
    val profilePictureUrl: LiveData<String> get() = _profilePictureUrl

    init {
        loadUserData()
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            _email.value = currentUser.email

            database.child(currentUser.uid).get().addOnSuccessListener {
                _username.value = it.child("username").value.toString()
                _profilePictureUrl.value = it.child("profilePictureUrl").value.toString()
            }.addOnFailureListener {
                _username.value = "Unknown"
                _profilePictureUrl.value = ""
            }
        }
    }

    fun updateUsername(newUsername: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            database.child(currentUser.uid).child("username").setValue(newUsername)
                .addOnSuccessListener {
                    _username.value = newUsername
                }
        }
    }

    fun uploadProfilePicture(uri: Uri) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val profilePictureRef = storage.child("${currentUser.uid}.jpg")
            profilePictureRef.putFile(uri).addOnSuccessListener {
                profilePictureRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    database.child(currentUser.uid).child("profilePictureUrl")
                        .setValue(downloadUrl.toString())
                    _profilePictureUrl.value = downloadUrl.toString()
                }
            }
        }
    }

    fun logout() {
        auth.signOut()
    }
}
