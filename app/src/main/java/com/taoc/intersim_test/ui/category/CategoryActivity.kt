package com.taoc.intersim_test.ui.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.ActivityCategoryBinding
import com.taoc.intersim_test.adapter.CategoryAdapter
import com.taoc.intersim_test.ui.detailjobs.DetailJobsActivity

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private val categoryViewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: ""
        binding.tvCategory.text = categoryName

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        fetchCategoryJobs(categoryName)
    }

    private fun setupRecyclerView() {
        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        categoryAdapter = CategoryAdapter(listOf()) { job ->
            val intent = Intent(this, DetailJobsActivity::class.java)
            intent.putExtra("Id", job.id)
            startActivity(intent)
        }
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun fetchCategoryJobs(category: String) {
        categoryViewModel.fetchJobsByCategory(category, 50) { jobs ->
            runOnUiThread {
                if (jobs.isNullOrEmpty()) {
                    Toast.makeText(this@CategoryActivity, "Job by category is empty", Toast.LENGTH_SHORT).show()
                } else {
                    categoryAdapter.updateJobs(jobs)
                }
                binding.pbCategory.visibility = View.GONE
            }
        }
    }
}