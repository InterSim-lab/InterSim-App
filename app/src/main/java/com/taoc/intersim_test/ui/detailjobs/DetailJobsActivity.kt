package com.taoc.intersim_test.ui.detailjobs

import android.content.Intent
import android.os.Bundle
import android.print.PrintJobId
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.Glide
import com.taoc.intersim_app.R
import com.taoc.intersim_test.data.database.AppDatabase
import com.taoc.intersim_test.data.database.ChatHistory
import com.taoc.intersim_test.ui.chat.ChatActivity
import com.taoc.intersim_test.data.response.DetailResponseItem
import com.taoc.intersim_test.data.retrofit.ApiConfig
import com.taoc.intersim_test.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailJobsActivity : AppCompatActivity() {

    private lateinit var jobLogo: ImageView
    private lateinit var jobTitle: TextView
    private lateinit var jobDescription: TextView
    private lateinit var jobPosition: TextView
    private lateinit var enterChatButton: Button
    private lateinit var appDatabase: AppDatabase

    private val detailViewModel: DetailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory(ApiConfig.getApiService())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jobs)

        appDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "chat_database"
        ).build()


        jobLogo = findViewById(R.id.jobLogo)
        jobTitle = findViewById(R.id.jobTitle)
        jobDescription = findViewById(R.id.jobDescription)
        jobPosition = findViewById(R.id.jobPosition)
        enterChatButton = findViewById(R.id.enterChatButton)

        val jobId = intent.getIntExtra("Id", -1)
        val imageUrl = intent.getStringExtra("ImageUrl") ?: ""

        if (jobId != -1) {
            detailViewModel.fetchJobDetails(jobId)
        }

        detailViewModel.jobDetail.observe(this) { jobDetail ->
            jobDetail?.let {
                displayJobDetails(it, imageUrl)
            }
        }

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun displayJobDetails(job: DetailResponseItem, imageUrl: String) {
        jobTitle.text = job.title
        jobPosition.text = job.company
        jobDescription.text = job.description
        Glide.with(this).load(job.urlImg).into(jobLogo)

        enterChatButton.setOnClickListener {
            saveChatHistory(job.id ?: 0, job.title?:"", job.company?: "", imageUrl)
            val intent = Intent(this, ChatActivity::class.java).apply {
                putExtra("id", job.id.toString())
                putExtra("imageUrl", imageUrl)
                putExtra("title", job.title)
                putExtra("company", job.company)
            }
            startActivity(intent)
        }
    }

    private fun saveChatHistory(jobId: Int, title: String, company: String, imageUrl: String) {
        val chatHistory = ChatHistory(
            jobId = jobId.toString(),
            message = "Chat started for job: $title",
            timestamp = System.currentTimeMillis(),
            title = title,
            company = company,
            imageUrl = imageUrl
        )
        lifecycleScope.launch(Dispatchers.IO) {
            appDatabase.chatHistoryDao().insertChat(chatHistory)
        }
    }
}