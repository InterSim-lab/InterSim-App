package com.taoc.intersim_test.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.taoc.intersim_test.ui.home.HomeFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.databinding.FragmentHomeBinding
import com.taoc.intersim_test.adapter.JobAdapter
import com.taoc.intersim_test.data.pref.UserPreferences
import com.taoc.intersim_test.ui.category.CategoryActivity
import com.taoc.intersim_test.ui.detailjobs.DetailJobsActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var jobAdapter: JobAdapter
    private lateinit var userPreferences: UserPreferences

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
        userPreferences = UserPreferences(requireContext())
        setupRecyclerView()
        observeViewModel()
        showLoading()
        loadUserProfile()
        homeViewModel.fetchJobs(limit = 20)
        setupListeners()
    }

    private fun setupListeners() {
        binding.HumanResource.setOnClickListener {
            navigateToCategoryActivity("Human Resource")
        }

        binding.IT.setOnClickListener {
            navigateToCategoryActivity("IT")
        }

        binding.Sales.setOnClickListener {
            navigateToCategoryActivity("Sales")
        }

        binding.Design.setOnClickListener {
            navigateToCategoryActivity("Design")
        }

        binding.SupplyChain.setOnClickListener {
            navigateToCategoryActivity("Supply Chain")
        }
    }


    private fun navigateToCategoryActivity(category: String) {
        val intent = Intent(requireContext(), CategoryActivity::class.java)
        intent.putExtra("CATEGORY_NAME", category)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        binding.viewTopJobs.layoutManager = LinearLayoutManager(context)
        jobAdapter = JobAdapter(listOf()) { job ->
            val intent = Intent(requireContext(), DetailJobsActivity::class.java)
            intent.putExtra("Id", job?.id)
            intent.putExtra("ImageUrl", job?.urlImg)
            startActivity(intent)
        }
        binding.viewTopJobs.adapter = jobAdapter
    }


    private fun observeViewModel() {
        homeViewModel.jobList.observe(viewLifecycleOwner) { jobs ->
            jobAdapter = JobAdapter(jobs) { job ->
                val intent = Intent(requireContext(), DetailJobsActivity::class.java)
                intent.putExtra("Id", job?.id)
                startActivity(intent)
            }
            binding.viewTopJobs.adapter = jobAdapter
            hideLoading()
        }
    }

    private fun loadUserProfile() {
        lifecycleScope.launch {
            userPreferences.userDetails.collect { (fullName, _) ->
                binding.nameProfile.text = "Hi, $fullName"
            }
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.viewTopJobs.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.viewTopJobs.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}