package com.taoc.intersim_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taoc.intersim_app.databinding.ItemCategoryBinding
import com.taoc.intersim_test.data.response.CategoryResponseItem

class CategoryAdapter(
    private var jobs: List<CategoryResponseItem>,
    private val onJobClick: (CategoryResponseItem) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(jobs[position])
    }

    override fun getItemCount(): Int = jobs.size

    fun updateJobs(newJobs: List<CategoryResponseItem>) {
        jobs = newJobs
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: CategoryResponseItem) {
            binding.categoryTitle.text = job.title
            binding.categoryCompany.text = job.company
            Glide.with(binding.categoryImage.context)
                .load(job.urlImg)
                .into(binding.categoryImage)
            binding.root.setOnClickListener {
                onJobClick(job)
            }
        }
    }
}