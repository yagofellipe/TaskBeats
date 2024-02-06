package com.comunidadedevspace.taskbeats.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.News

class NewsListFragment : Fragment() {

    private val adapter: TaskNewsAdapter by lazy {
        TaskNewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_news)
        recyclerView.adapter = adapter

        val newsList = listOf<News>(
            News("Title 1", "https://via.placeholder.com/150"),
            News("Title 2", "https://via.placeholder.com/150"),
            News("Title 3", "https://via.placeholder.com/150"),
            News("Title 4", "https://via.placeholder.com/150"),
            News("Title 5", "https://via.placeholder.com/150"),
            News("Title 6", "https://via.placeholder.com/150"),
            News("Title 7", "https://via.placeholder.com/150"),
            News("Title 8", "https://via.placeholder.com/150"),
            News("Title 9", "https://via.placeholder.com/150"),
            News("Title 10", "https://via.placeholder.com/150"),
        )
        adapter.submitList(newsList)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsListFragment().apply {
                }
            }

}