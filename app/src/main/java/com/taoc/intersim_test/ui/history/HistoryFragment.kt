package com.taoc.intersim_test.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.taoc.intersim_app.databinding.FragmentHistoryBinding
import com.taoc.intersim_test.adapter.HistoryAdapter
import com.taoc.intersim_test.data.database.AppDatabase
import com.taoc.intersim_test.data.response.JobsResponseItem
import com.taoc.intersim_test.ui.chat.ChatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var appDatabase: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyAdapter = HistoryAdapter(listOf()) { jobId ->
            val intent = Intent(requireContext(), ChatActivity::class.java).apply {
                putExtra("id", jobId)
            }
            startActivity(intent)
        }
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }

        appDatabase = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "chat_database"
        ).build()

        fetchChatHistory()
    }

    private fun fetchChatHistory() {
        binding.pgHistory.visibility = View.VISIBLE
        lifecycleScope.launch(Dispatchers.IO) {
            val chatList = appDatabase.chatHistoryDao().getAllChats()
            val jobList = chatList.map { chatHistory ->
                JobsResponseItem(
                    title = chatHistory.title,
                    company = chatHistory.company,
                    urlImg = chatHistory.imageUrl
                )
            }
            withContext(Dispatchers.Main) {
                binding.pgHistory.visibility = View.GONE
                if (jobList.isNotEmpty()) {
                    historyAdapter.updateData(jobList)
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.textEmptyHistory.visibility = View.GONE
                } else {
                    binding.rvHistory.visibility = View.GONE
                    binding.textEmptyHistory.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}