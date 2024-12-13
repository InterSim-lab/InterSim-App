package com.taoc.intersim_app.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taoc.intersim_app.databinding.ActivityRegisterBinding
import com.taoc.intersim_test.data.pref.UserPreferences
import com.taoc.intersim_test.login.LoginActivity
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        binding.apply {
            buttonRegister.setOnClickListener {
                val username = editTextTextUsernameAddress.text.toString()
                val email = editTextTextEmailAddress.text.toString()
                val password = editTextTextPassword.text.toString()

                if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    registerUser(username, email, password)
                } else {
                    Toast.makeText(this@RegisterActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        lifecycleScope.launch {
            userPreferences.saveUserDetails(username, email)
            userPreferences.saveRegisteredUser(email, password)

            userPreferences.saveLoginState(true)

            Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}