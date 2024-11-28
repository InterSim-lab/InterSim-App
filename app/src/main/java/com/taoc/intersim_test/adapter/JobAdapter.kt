package com.taoc.intersim_test.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.taoc.intersim_app.R
import com.taoc.intersim_test.data.response.JobsResponseItem

class JobAdapter(
    private val jobList: List<JobsResponseItem?>,
    private val onClick: (JobsResponseItem?) -> Unit
) : RecyclerView.Adapter<JobAdapter.JobViewHolder>() {

    class JobViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.jobTitle)
        val company: TextView = itemView.findViewById(R.id.jobCompany)
        val image: ImageView = itemView.findViewById(R.id.jobImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return JobViewHolder(view)
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val job = jobList[position]
        holder.title.text = job?.title
        holder.company.text = job?.company
        Glide.with(holder.itemView.context).load(job?.urlImg).into(holder.image)

        holder.itemView.setOnClickListener {
            onClick(job)
        }
    }

    override fun getItemCount(): Int = jobList.size
}