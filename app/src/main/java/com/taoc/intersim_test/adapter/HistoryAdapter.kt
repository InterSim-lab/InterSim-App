package com.taoc.intersim_test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taoc.intersim_app.R
import com.taoc.intersim_app.databinding.ItemHistoryBinding
import com.taoc.intersim_test.data.response.JobsResponseItem

class HistoryAdapter(
    private var jobList: List<JobsResponseItem>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(job: JobsResponseItem) {
            binding.historyTitle.text = job.title
            binding.historyCompany.text = job.company
            Glide.with(binding.historyImage.context)
                .load(job.urlImg)
                .placeholder(R.drawable.bangkit_ph)
                .into(binding.historyImage)
            itemView.setOnClickListener {
                onItemClick(job.id.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(jobList[position])
    }

    override fun getItemCount(): Int = jobList.size

    fun updateData(newJobList: List<JobsResponseItem>) {
        jobList = newJobList
        notifyDataSetChanged()
    }
}