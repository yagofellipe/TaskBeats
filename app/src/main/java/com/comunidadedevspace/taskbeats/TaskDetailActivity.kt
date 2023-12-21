package com.comunidadedevspace.taskbeats

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import java.io.Serializable

class TaskDetailActivity : AppCompatActivity() {
    companion object{
        const val TASK_DETAIL_EXTRA = "task.extra.detail"

        fun start(context: Context, task: Task): Intent{
            val intent = Intent(context, TaskDetailActivity::class.java)
                .apply {
                    putExtra(TaskDetailActivity.TASK_DETAIL_EXTRA, task)
                }
            return intent
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)


        val task: Task? = intent.getSerializableExtra(TASK_DETAIL_EXTRA) as Task?
        requireNotNull(task)
        val tvTitle = findViewById<TextView>(R.id.tv_task_title_detail)
        tvTitle.text = task?.title
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.task_menu_detail, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_task -> {
                val intent = Intent()
                    .apply {
                        putExtra("resultado_detail", "resultado")
                    }
                setResult(Activity.RESULT_OK, intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }

        return true
    }
}