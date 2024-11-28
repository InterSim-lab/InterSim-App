package com.taoc.intersim_app.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.taoc.intersim_app.databinding.ActivityRegisterBinding
<<<<<<< HEAD
import com.taoc.intersim_test.login.LoginActivity
=======
import com.taoc.login.LoginActivity
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            buttonRegister.setOnClickListener {
                Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }
}