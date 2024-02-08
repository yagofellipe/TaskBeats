package com.comunidadedevspace.taskbeats.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.comunidadedevspace.taskbeats.TaskBeatsApplication
import com.comunidadedevspace.taskbeats.data.Task
import com.comunidadedevspace.taskbeats.data.TaskDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskDetailViewModel(
    private val taskDao: TaskDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {

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
        viewModelScope.launch {
            task?.let { task ->
                taskDao.deleteTask(task)
            }

        }
    }

    private fun updateIntoDataBase(task: Task){
        viewModelScope.launch {
            taskDao.updateTask(task)

        }
    }

    private fun insertIntoDataBase(task: Task){
        viewModelScope.launch {
            taskDao.insert(task)

        }
    }

    companion object {
        fun getVMFactory(application: Application): ViewModelProvider.Factory {
            val dataBaseInstance = (application as? TaskBeatsApplication)?.getAppDataBase()


            val dao = dataBaseInstance?.taskDao() ?: throw Exception("Database not found")

            val factory = object : ViewModelProvider.Factory {

                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TaskDetailViewModel(dao) as T
                }
            }
            return factory
        }
    }

}


/*
    O acesso em um database pode ser custoso e levar tempo. Por iss,
    é necessário rodar as requisições em uma outra thread, para a main thread não ficar travada.
    Caso não utilize, provavelmente, o app vai crashar.
    Ao passar uma função SUSPEND, não é preciso usar a DISPATCHER.
    SUSPEND: quando uma função é suspend, significa que ela pode ser suspensa sem bloquear
    a thread.
 */