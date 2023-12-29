package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
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

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "taskbeat-database"
        ).build()
    }

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
            if (taskAction.actionType == ActionType.DELETE.name) {
                /*val newList = arrayListOf<Task>().apply {
                    addAll(taskList)
                }

                newList.remove(task)

                showMessage(ctnContent, "Item deleted ${task.title}")
                if (newList.size == 0) {
                    ctnContent.visibility = View.VISIBLE
                }

                adapter.submitList(newList)
                taskList = newList*/
            }else if(taskAction.actionType == ActionType.CREATE.name){
                insertIntoDataBase(task)
            }else if(taskAction.actionType == ActionType.UPDATE.name){
               /* val newList = arrayListOf<Task>().apply {
                    addAll(taskList)
                }

                val tempEmptyList = arrayListOf<Task>()
                taskList.forEach{
                    if(it.id == task.id){
                        val newItem = Task(it.id, task.title, task.description)
                        tempEmptyList.add(newItem)
                    } else {
                        tempEmptyList.add(it)
                    }
                }

                showMessage(ctnContent, "Item updated ${task.title}")
                adapter.submitList(tempEmptyList)
                taskList = tempEmptyList*/
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)


        listFromDataBase()

        ctnContent = findViewById(R.id.ctn_content)

        val rvTask: RecyclerView = findViewById(R.id.rv_task_list)



        rvTask.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fab_add)
        fab.setOnClickListener {
            openTaskListDetail(null)
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