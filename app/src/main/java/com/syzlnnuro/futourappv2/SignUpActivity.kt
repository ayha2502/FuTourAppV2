package com.syzlnnuro.futourappv2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.syzlnnuro.futourappv2.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

                        // Default URL untuk gambar profil
                        val defaultPhotoUrl = "https://example.com/default-avatar.png"
                        val user = mapOf("username" to username, "photoUrl" to defaultPhotoUrl)

                        databaseRef.setValue(user).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Signup Successful. Please login.", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "Database Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Signup Failed: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Fields Cannot Be Empty", Toast.LENGTH_SHORT).show()
            }
        }

        binding.footerLogin.setOnClickListener {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }
}
