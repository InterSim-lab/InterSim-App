package com.taoc.intersim_test.ui.detailjobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.taoc.intersim_app.R
import com.taoc.intersim_test.chat.ChatActivity
import com.taoc.intersim_test.data.response.JobsResponseItem
import com.taoc.intersim_test.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailJobsActivity : AppCompatActivity() {

    private lateinit var jobLogo: ImageView
    private lateinit var jobTitle: TextView
    private lateinit var jobCompany: TextView
    private lateinit var jobDescription: TextView
    private lateinit var enterChatButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_jobs)

        jobLogo = findViewById(R.id.jobLogo)
        jobTitle = findViewById(R.id.jobTitle)
        jobCompany = findViewById(R.id.jobPosition)
        jobDescription = findViewById(R.id.jobDescription)
        enterChatButton = findViewById(R.id.enterChatButton)

        val jobId = intent.getStringExtra("Id")
        fetchJobDetails(jobId)

        val enterChatButton: Button = findViewById(R.id.enterChatButton)
        enterChatButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("Id", jobId)
            startActivity(intent)
        }
        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun fetchJobDetails(Id: String?) {
        if (Id != null) {
            ApiConfig.getApiService().getJobDetails(Id).enqueue(object : Callback<JobsResponseItem> {
                override fun onResponse(call: Call<JobsResponseItem>, response: Response<JobsResponseItem>) {
                    if (response.isSuccessful) {
                        response.body()?.let { job ->
                            displayJobDetails(job)
                        }
                    }
                }

                override fun onFailure(call: Call<JobsResponseItem>, t: Throwable) {
                    // Tangani kesalahan
                }
            })
        }
    }

    private fun displayJobDetails(job: JobsResponseItem) {
        jobTitle.text = job.title
        jobCompany.text = job.company
        jobDescription.text = job.description
        Glide.with(this).load(job.urlImg).into(jobLogo)
    }
}