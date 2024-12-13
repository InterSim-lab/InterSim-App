package com.taoc.intersim_test.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taoc.intersim_app.IntroActivity
import com.taoc.intersim_app.R
import com.taoc.intersim_test.MainActivity
import com.taoc.intersim_test.data.pref.UserPreferences
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        userPreferences = UserPreferences(this)
        Handler(Looper.getMainLooper()).postDelayed({
            checkLoginStatus()
        }, 2000)
        setContentView(R.layout.activity_splash)
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            userPreferences.isLoggedIn.collect { isLoggedIn ->
                if (isLoggedIn) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                } else {
                    startActivity(Intent(this@SplashActivity, IntroActivity::class.java))
                }
                finish()
            }
        }
    }
}