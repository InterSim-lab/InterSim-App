package com.taoc.intersim_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taoc.intersim_app.databinding.ActivityIntroBinding
<<<<<<< HEAD
import com.taoc.intersim_test.login.LoginActivity
=======
import com.taoc.login.LoginActivity
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410

class IntroActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            button.setOnClickListener {
                val intent = Intent(this@IntroActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}