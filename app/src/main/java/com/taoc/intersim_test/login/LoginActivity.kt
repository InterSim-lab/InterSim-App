package com.taoc.intersim_test.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taoc.intersim_app.databinding.ActivityLoginBinding
import com.taoc.intersim_app.register.RegisterActivity
import com.taoc.intersim_test.MainActivity
import com.taoc.intersim_test.data.pref.UserPreferences
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        binding.apply {
            buttonLogin.setOnClickListener {
                val email = editTextTextEmailAddress.text.toString()
                val password = editTextTextPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginUser(email, password)
                } else {
                    Toast.makeText(this@LoginActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }

            textRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            userPreferences.getRegisteredUsers().collect { registeredUsers ->
                val validPassword = registeredUsers[email]

                if (validPassword != null && validPassword == password) {
                    userPreferences.saveLoginState(true)
                    userPreferences.savedToken("your_token_here")
                    userPreferences.saveUserDetails(
                        fullName = extractNameFromEmail(email),
                        email = email
                    )

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Invalid email or password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun extractNameFromEmail(email: String): String {
        return email.split("@").first().capitalize()
    }
}