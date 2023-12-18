package com.comunidadedevspace.taskbeats

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)

        val taskList = listOf<Task>(
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task(" 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),
            Task("Titlesvsgbweegb 1", "Agatha"),

            )

        val adapter: TaskListAdapter = TaskListAdapter(taskList, ::openTaskDetailView)

        rvTask.adapter = adapter
    }

    fun openTaskDetailView(task: Task){
        val intent = Intent(this, TaskDetailActivity::class.java)
            .apply {
                putExtra(TaskDetailActivity.TASK_TITLE_EXTRA, task.title)
            }
        startActivity(intent)
    }
}