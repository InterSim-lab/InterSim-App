package com.taoc.intersim_test.chat

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.taoc.intersim_app.R
import com.taoc.intersim_test.ui.detailjobs.DetailJobsActivity

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

<<<<<<< HEAD
        val jobId = intent.getStringExtra("jobId") // Ambil ID pekerjaan

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish() // Kembali ke DetailJobsActivity
        }
    }

=======
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, DetailJobsActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410
}