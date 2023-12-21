package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){

        }
    }
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

    private fun openTaskDetailView(task: Task){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)

    }
}