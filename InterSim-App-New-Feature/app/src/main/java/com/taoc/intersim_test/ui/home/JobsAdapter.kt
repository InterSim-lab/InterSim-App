package com.taoc.intersim_test.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taoc.intersim_app.R
import com.taoc.intersim_test.data.retrofit.Job

class JobsAdapter(private var jobList: List<Job>): RecyclerView.Adapter<JobsAdapter.JobsViewHolder>() {

    class JobsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.jobTitle)
        val description: TextView = itemView.findViewById(R.id.jobDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_topjobs, parent, false)
        return JobsViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job = jobList[position]
        holder.apply {
            title.text = job.title
            description.text = job.description
        }
    }

    override fun getItemCount(): Int = jobList.size

    fun updateJobs(newJobs: List<Job>) {
        jobList = newJobs
        notifyDataSetChanged()
    }
}