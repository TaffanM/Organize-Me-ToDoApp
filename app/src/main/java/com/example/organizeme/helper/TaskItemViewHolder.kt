package com.example.organizeme.helper

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.organizeme.databinding.TaskLayoutBinding

class TaskItemViewHolder(val context: Context, val binding: TaskLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindTaskItem(taskItem: TaskItem){
        binding.toDoTitle.text = taskItem.name
    }
}