package com.taoc.intersim_app.ui.home

<<<<<<< HEAD
import android.content.Intent
import android.os.Bundle
import android.util.Log
=======
import android.os.Bundle
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
<<<<<<< HEAD
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.FragmentHomeBinding
import com.taoc.intersim_test.adapter.JobAdapter
import com.taoc.intersim_test.data.response.CategoryResponseItem
import com.taoc.intersim_test.ui.detailjobs.DetailJobsActivity
import com.taoc.intersim_test.ui.home.HomeViewModel
import com.taoc.intersim_test.ui.search.SearchFragment
=======
import com.taoc.intersim_app.databinding.FragmentHomeBinding
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

<<<<<<< HEAD
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var jobAdapter: JobAdapter

=======
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410
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
<<<<<<< HEAD
        setupRecyclerView()
        observeViewModel()
        showLoading()
        homeViewModel.fetchJobs(limit = 20)
    }

    private fun setupRecyclerView() {
        binding.viewTopJobs.layoutManager = LinearLayoutManager(context)
        jobAdapter = JobAdapter(listOf()) { job -> }
        binding.viewTopJobs.adapter = jobAdapter
    }

    private fun observeViewModel() {
        homeViewModel.jobList.observe(viewLifecycleOwner) { jobs ->
            jobAdapter = JobAdapter(jobs) { job ->
                // Pindah ke DetailJobsActivity saat pekerjaan diklik
                val intent = Intent(requireContext(), DetailJobsActivity::class.java)
                intent.putExtra("jobId", job?.id) // Kirim ID pekerjaan
                startActivity(intent)
            }
            binding.viewTopJobs.adapter = jobAdapter
            hideLoading()
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.viewTopJobs.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.viewTopJobs.visibility = View.VISIBLE
=======

//        binding.imagebtnProfile.setOnClickListener {
//            val intent = Intent(requireContext(), ProfileFragment::class.java)
//            startActivity(intent)
//        }
//
//        binding.searchText.setOnClickListener {
//            val intent = Intent(requireContext(), SearchFragment::class.java)
//            startActivity(intent)
//        }
>>>>>>> 01c65e76144a30efd66b83218116a32fceda2410
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}