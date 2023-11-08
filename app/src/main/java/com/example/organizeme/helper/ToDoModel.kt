package com.example.organizeme.helper

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class ToDoModel: ViewModel() {

    var taskItems = MutableLiveData<MutableList<TaskItem>>()

    init {
        taskItems.value = mutableListOf()
    }

    fun addTaskItem(newTask: TaskItem){
        val list = taskItems.value
        list!!.add(newTask)
        taskItems.postValue(list)
    }

    fun updateTaskitem(id: UUID, name: String, dueTime: LocalTime?, dueDate: LocalDate?){
        val list = taskItems.value
        val task = list!!.find { it.id == id}!!
        task.name = name
        task.dueTime = dueTime
        task.dueDate = dueDate
    }

    fun setCompleted(taskItem: TaskItem){
        val list = taskItems.value
        val task = list!!.find { it.id == taskItem.id}!!
        if (task.dueDate == null)
            task.dueDate = LocalDate.now()
        taskItems.postValue(list)
    }


}