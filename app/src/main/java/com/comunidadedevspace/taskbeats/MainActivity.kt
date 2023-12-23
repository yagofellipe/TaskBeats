package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.app.Notification.Action
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private var taskList = arrayListOf(
        Task(0,"Titlesvsgbweegb 1", "Agatha"),
        Task(1,"Titlesvsgbweegb 1", "Agatha"),
        )

    private lateinit var ctnContent : LinearLayout

    private val adapter: TaskListAdapter = TaskListAdapter(::openTaskDetailView)


    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data
            val taskAction = requireNotNull(
                data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            )
            val task : Task = taskAction.task
            taskList.remove(task)

            if (taskList.size == 0){
                ctnContent.visibility = View.VISIBLE
            }

            adapter.submit(taskList)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ctnContent = findViewById(R.id.ctn_content)

        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)




        adapter.submit(taskList)

        rvTask.adapter = adapter
    }

    private fun openTaskDetailView(task: Task){
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)

    }
}

sealed class ActionType : Serializable{
    object DELETE : ActionType()
    object UPDATE : ActionType()
    object CREATE : ActionType()
}

data class TaskAction(
    val task: Task,
    val actionType: ActionType
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"