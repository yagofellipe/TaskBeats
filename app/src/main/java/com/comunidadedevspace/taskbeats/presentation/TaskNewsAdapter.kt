package com.comunidadedevspace.taskbeats.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.comunidadedevspace.taskbeats.R
import com.comunidadedevspace.taskbeats.data.News

class TaskNewsAdapter(): ListAdapter<News, NewsListViewHolder>(TaskNewsAdapter) {

    companion object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListViewHolder {
        val view: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsListViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)
    }
}

class NewsListViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val tvTitle = view.findViewById<TextView>(R.id.title)
    private val imgNews = view.findViewById<ImageView>(R.id.img_news)

    fun bind(news:News) {
        tvTitle.text = news.title
        imgNews.load(news.imgUrl)


    }
}