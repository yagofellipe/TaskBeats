package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskListViewModel(private val taskDao: TaskDao): ViewModel(){

    val taskListLiveData: LiveData<List<Task>> = taskDao.getAll()
    fun execute(taskAction: TaskAction){
        when (taskAction.actionType) {
            ActionType.DELETE.name -> {
                deleteIntoDataBase(taskAction.task)
            }
            ActionType.CREATE.name -> {
                insertIntoDataBase(taskAction.task)
            }
            ActionType.UPDATE.name -> {
                updateIntoDataBase(taskAction.task)
            }
        }
    }
    private fun deleteIntoDataBase(task: Task){
        viewModelScope.launch(Dispatchers.IO)  {
            task?.let { task ->
                taskDao.deleteTask(task)
            }

        }
    }

    private fun updateIntoDataBase(task: Task){
        viewModelScope.launch(Dispatchers.IO)  {
            taskDao.updateTask(task)

        }
    }

    private fun insertIntoDataBase(task: Task){
       viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(task)

        }
    }
    companion object {
        fun create(application: Application): TaskListViewModel {
            val dataBaseInstance = (application as TaskBeatsApplication).getAppDataBase()
            val dao = dataBaseInstance.taskDao()
            return TaskListViewModel(dao)
        }
    }
}