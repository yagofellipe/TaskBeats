package com.comunidadedevspace.taskbeats.presentation

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.AppDatabase
import com.comunidadedevspace.taskbeats.data.Task
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.io.Serializable

class MainActivity : AppCompatActivity() {



    private lateinit var ctnContent : LinearLayout

    private val adapter: TaskListAdapter by lazy { TaskListAdapter(::onListItemClicked) }

    private val viewModel: TaskListViewModel = TaskListViewModel.create(application)

    lateinit var db :AppDatabase

    private val dao by lazy { db.taskDao() }

    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val taskAction = requireNotNull(
                data?.getSerializableExtra(TASK_ACTION_RESULT) as TaskAction
            )
            val task: Task = taskAction.task
            when (taskAction.actionType) {
                ActionType.DELETE.name -> {
                    deleteIntoDataBase(task)
                }
                ActionType.CREATE.name -> {
                    insertIntoDataBase(task)
                }
                ActionType.UPDATE.name -> {
                    updateIntoDataBase(task)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)




        ctnContent = findViewById(R.id.ctn_content)

        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)



        rvTask.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail(null)
        }
    }

    override fun onStart() {
        super.onStart()
        db = (application as TaskBeatsApplication).db

        listFromDataBase()
    }

    private fun deleteIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            task?.let { task ->
                dao.deleteTask(task)
            }
            listFromDataBase()
        }
    }

    private fun updateIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.updateTask(task)
            listFromDataBase()
        }
    }

    private fun insertIntoDataBase(task: Task){
        CoroutineScope(IO).launch {
            dao.insert(task)
            listFromDataBase()
        }
    }

    private fun listFromDataBase(){
        CoroutineScope(Dispatchers.IO).launch {
            val myDataBaseList: List<Task> = dao.getAll()
            adapter.submitList(myDataBaseList)

            if (myDataBaseList.isEmpty()) {
                ctnContent.visibility = View.VISIBLE
            }else{
                ctnContent.visibility = View.GONE
            }
        }
    }

    private fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show()
    }

    private fun onListItemClicked(task: Task) {
        openTaskListDetail(task)
    }

    private fun openTaskListDetail(task: Task? = null) {
        val intent = TaskDetailActivity.start(this, task)
        startForResult.launch(intent)

    }
}

enum class ActionType {
    DELETE,
    UPDATE,
    CREATE
}

data class TaskAction(
    val task: Task,
    val actionType: String
) : Serializable

const val TASK_ACTION_RESULT = "TASK_ACTION_RESULT"