package com.taoc.intersim_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taoc.intersim_app.databinding.ItemSearchBinding
import com.taoc.intersim_test.data.response.SearchResponseItem

class SearchAdapter(
    private val onItemClick: (SearchResponseItem?) -> Unit
) : ListAdapter<SearchResponseItem, SearchAdapter.JobViewHolder>(JobDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JobViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = getItem(position)
        holder.bind(job)
    }

    inner class JobViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val job = bindingAdapterPosition.takeIf { it != RecyclerView.NO_POSITION }?.let { position ->
                    getItem(position)
                }
                job?.let { onItemClick(it) }
            }
        }

        fun bind(job: SearchResponseItem?) {
            binding.searchTitle.text = job?.title
            binding.searchCompany.text = job?.company
            Glide.with(binding.searchImage.context)
                .load(job?.urlImg)
                .into(binding.searchImage)
        }
    }

    class JobDiffCallback : DiffUtil.ItemCallback<SearchResponseItem>() {
        override fun areItemsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
            return oldItem == newItem
        }
    }
}