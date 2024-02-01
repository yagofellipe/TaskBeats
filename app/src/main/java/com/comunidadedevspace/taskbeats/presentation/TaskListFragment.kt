package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.Task


/**
 * A simple [Fragment] subclass.
 * Use the [TaskListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskListFragment : Fragment() {

    private lateinit var ctnContent : LinearLayout

    private val adapter: TaskListAdapter by lazy { TaskListAdapter(::openTaskListDetail) }

    private val viewModel: TaskListViewModel by lazy {
    TaskListViewModel.create(requireActivity().application) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ctnContent = view.findViewById(R.id.ctn_content)

        val rvTask = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.rv_task_list)

        rvTask.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        listFromDataBase()
    }

    private fun listFromDataBase(){
        val listObserver = Observer<List<Task>>{ listTask ->
            if(listTask.isEmpty()){
                ctnContent.visibility = View.VISIBLE
            } else{
                ctnContent.visibility = View.GONE
            }
            adapter.submitList(listTask)
        }
        viewModel.taskListLiveData.observe(this, listObserver)

    }
    private fun openTaskListDetail(task: Task) {
        val intent = TaskDetailActivity.start(requireContext(), task)
        requireActivity().startActivity(intent)

    }
    companion object {
        @JvmStatic
        fun newInstance() =
            TaskListFragment().apply {
            }
    }
}