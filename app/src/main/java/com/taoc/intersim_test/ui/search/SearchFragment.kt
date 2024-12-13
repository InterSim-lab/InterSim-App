package com.taoc.intersim_test.ui.search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.FragmentSearchBinding
import com.taoc.intersim_test.adapter.SearchAdapter
import com.taoc.intersim_test.data.retrofit.ApiConfig
import com.taoc.intersim_test.ui.detailjobs.DetailJobsActivity
import com.taoc.intersim_test.viewmodel.ViewModelFactory
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchAdapter: SearchAdapter
    private val searchViewModel: SearchViewModel by viewModels {
        ViewModelFactory(ApiConfig.getApiService())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()

        lifecycleScope.launch {
            searchViewModel.jobList.collect { jobs ->
                binding.progressBar.visibility = if (jobs.isEmpty()) View.GONE else View.GONE
                binding.rvSearch.visibility = if (jobs.isEmpty()) View.GONE else View.VISIBLE
                searchAdapter.submitList(jobs)
            }
        }
    }


    private fun setupRecyclerView() {
        binding.rvSearch.layoutManager = LinearLayoutManager(context)
        searchAdapter = SearchAdapter { job ->
            val intent = Intent(requireContext(), DetailJobsActivity::class.java)
            intent.putExtra("Id", job?.id)
            startActivity(intent)
        }
        binding.rvSearch.adapter = searchAdapter
    }

    private fun setupSearchView() {
        binding.scSearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    binding.progressBar.visibility = View.VISIBLE
                    searchJobs(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        val searchTextView = binding.scSearch.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
        searchTextView.setTextColor(Color.BLACK)
        searchTextView.setHintTextColor(Color.GRAY)

        binding.scSearch.setOnQueryTextFocusChangeListener { _, hasFocus ->
            val searchIcon = binding.scSearch.findViewById<ImageView>(androidx.appcompat.R.id.search_mag_icon)
            if (hasFocus) {
                searchIcon.setImageResource(R.drawable.search)
            } else {
                searchIcon.setImageResource(R.drawable.ic_search_black)
            }
        }
    }

    private fun searchJobs(title: String) {
        lifecycleScope.launch {
            searchViewModel.searchJobsByTitle(title).collect { jobs ->
                searchAdapter.submitList(jobs)
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}