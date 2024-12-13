package com.taoc.intersim_app.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.databinding.FragmentHomeBinding
import com.taoc.intersim_test.data.retrofit.ApiConfig
import com.taoc.intersim_test.data.retrofit.Job
import com.taoc.intersim_test.ui.home.JobsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var jobsAdapter: JobsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchJobs()
    }

    private fun setupRecyclerView() {
        binding.apply {
            viewTopJobs.layoutManager = LinearLayoutManager(requireContext())
            jobsAdapter = JobsAdapter(emptyList())
            viewTopJobs.adapter = jobsAdapter
            viewTopJobs.setHasFixedSize(true)
        }
    }

    private fun fetchJobs() {
        Log.d("HomeFragment", "Fetching jobs from API") // Tambahkan log
        binding.progressBarTopJobs.visibility = View.VISIBLE

        ApiConfig.apiService.getJobs(20).enqueue(object : Callback<List<Job>> {
            override fun onResponse(call: Call<List<Job>>, response: Response<List<Job>>) {
                binding.progressBarTopJobs.visibility = View.GONE

                if (response.isSuccessful) {
                    response.body()?.let { jobs ->
                        jobsAdapter.updateJobs(jobs)
                    } ?: run {
                        Log.e("HomeFragment", "Response body is null")
                    }
                } else {
                    Log.e("HomeFragment", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Job>>, t: Throwable) {
                binding.progressBarTopJobs.visibility = View.GONE
                Log.e("HomeFragment", "Error fetching jobs", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}