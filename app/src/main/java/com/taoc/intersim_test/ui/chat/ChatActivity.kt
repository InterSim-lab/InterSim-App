package com.taoc.intersim_test.ui.chat

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.ActivityChatBinding
import com.taoc.intersim_test.adapter.ChatAdapter
import com.taoc.intersim_test.data.database.AppDatabase
import com.taoc.intersim_test.data.database.ChatHistory
import com.taoc.intersim_test.data.response.QuestionsItem

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private val chatViewModel: ChatViewModel by viewModels {
        val database = AppDatabase.getDatabase(applicationContext)
        ChatViewModelFactory(database.chatHistoryDao())
    }
    private lateinit var chatAdapter: ChatAdapter
    private val chatMessages = mutableListOf<ChatMessage>()
    private var questions: List<QuestionsItem> = listOf()
    private var currentQuestionIndex = 0

    private var jobId: Int = 0
    private lateinit var imageUrl: String
    private lateinit var jobTitle: String
    private lateinit var jobCompany: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jobId = intent.getStringExtra("id")?.toIntOrNull() ?: 0
        imageUrl = intent.getStringExtra("imageUrl") ?: ""
        jobTitle = intent.getStringExtra("title") ?: ""
        jobCompany = intent.getStringExtra("company") ?: ""

        binding.toolbarTitle.text = jobTitle

        binding.progressBar.visibility = View.VISIBLE

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        setupViews()
        generateQuestions()
    }

    private fun generateQuestions() {
        if (jobId != 0) {
            binding.progressBar.visibility = View.VISIBLE

            chatViewModel.generateQuestions(
                jobId = jobId,
                onSuccess = { response ->
                    binding.progressBar.visibility = View.GONE

                    questions = response.questions?.filterNotNull() ?: emptyList()

                    Log.d("ChatActivity", "Generated Questions: ${questions.size}")
                    Log.d("ChatActivity", "Job ID: $jobId")
                    Log.d("ChatActivity", "Job Title: ${response.title}")
                    Log.d("ChatActivity", "Job Company: ${response.company}")

                    if (questions.isNotEmpty()) {
                        showNextQuestion()
                    } else {
                        showErrorAndFinish("Tidak ada pertanyaan yang dihasilkan")
                    }
                },
                onError = { errorMessage ->
                    showErrorAndFinish(errorMessage)
                }
            )
        } else {
            showErrorAndFinish("ID Pekerjaan tidak valid")
        }
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatMessages)
        binding.chatRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ChatActivity)
            adapter = chatAdapter
        }
    }

    private fun setupViews() {
        binding.toolbarTitle.text = intent.getStringExtra("title") ?: "Interview Chat"

        binding.sendButton.setOnClickListener {
            val message = binding.messageInput.text.toString().trim()
            if (message.isNotEmpty()) {
                sendMessage(message)
                binding.messageInput.text.clear()
                if (currentQuestionIndex < questions.size) {
                    showNextQuestion()
                }
            } else {
                Toast.makeText(this, "Silakan masukkan pesan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendMessage(message: String) {
        chatMessages.add(ChatMessage(message, true))
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        binding.chatRecyclerView.scrollToPosition(chatMessages.size - 1)
    }

    private fun showNextQuestion() {
        if (currentQuestionIndex < questions.size) {
            val question = questions[currentQuestionIndex]
            question.question?.let { questionText ->
                chatMessages.add(ChatMessage(questionText, false))
                chatAdapter.notifyItemInserted(chatMessages.size - 1)
                binding.chatRecyclerView.scrollToPosition(chatMessages.size - 1)
            }
            currentQuestionIndex++
        } else {
            Toast.makeText(this, "Interview Done!", Toast.LENGTH_SHORT).show()
            saveChatHistory()
            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 2000)
        }
    }

    private fun saveChatHistory() {
        val chatHistory = ChatHistory(
            jobId = jobId.toString(),
            message = chatMessages.joinToString("\n") { it.message },
            timestamp = System.currentTimeMillis(),
            title = jobTitle,
            company = jobCompany,
            imageUrl = imageUrl
        )
//        chatViewModel.saveChatHistory(chatHistory)
    }

    private fun showErrorAndFinish(message: String) {
        binding.progressBar.visibility = View.GONE

        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> finish() }
            .setCancelable(false)
            .show()
    }
}