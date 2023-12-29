package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Task


class TaskListAdapter(
    private val openTaskDetailView: (task: Task) -> Unit
) :  androidx.recyclerview.widget.ListAdapter<Task, TaskListViewHolder>(TaskListAdapter){

    private var listTask: List<Task> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {

        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return  TaskListViewHolder(view)
    }



    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, openTaskDetailView)

    }

    private companion object : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

class TaskListViewHolder(private val view: View): RecyclerView.ViewHolder(view){

    private val tvTitle = view.findViewById<TextView>(R.id.tv_task_title)
    private val tvDescription = view.findViewById<TextView>(R.id.tv_task_description)

    fun bind(task: Task,
             openTaskDetailView: (task: Task) -> Unit){
        tvTitle.text = task.title
        tvDescription.text = task.description

        view.setOnClickListener {
            openTaskDetailView.invoke(task)
        }
    }
}

