package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Task
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val floatActionButton = findViewById<FloatingActionButton>(R.id.FloatingActionBottom)
        val taskListFragment = TaskListFragment.newInstance()
        val newsListFragment = NewsListFragment.newInstance()

        floatActionButton.setOnClickListener{
            openTaskListDetail()
        }

        supportFragmentManager.commit {
            replace(R.id.fragment_container_view, taskListFragment)
            setReorderingAllowed(true)
        }

        bottomNavView.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.task_list -> taskListFragment
                R.id.news_list -> newsListFragment
                else -> taskListFragment
            }


            supportFragmentManager.commit {
                replace(R.id.fragment_container_view, fragment)
                setReorderingAllowed(true)
            }
            true

        }

    }
    private fun openTaskListDetail(task: Task? = null) {
        val intent = TaskDetailActivity.start(this, null)
        startActivity(intent)

    }
}