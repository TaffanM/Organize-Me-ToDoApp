package com.example.organizeme.helper

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.organizeme.databinding.FragmentNewTaskBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDate
import java.time.LocalTime

class ToDoNewTask(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskBinding
    private lateinit var taskViewModel: ToDoModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        taskViewModel = ViewModelProvider(activity).get(ToDoModel::class.java)


        if (taskItem != null){
            binding.taskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            if(taskItem!!.dueTime != null || taskItem!!.dueDate != null){
                dueTime = taskItem!!.dueTime!!
                dueDate = taskItem!!.dueDate!!
//                updateTimeButtonText()
            }
        } else {
            binding.taskTitle.text = "Create Task"
        }


        binding.saveButton.setOnClickListener {
            saveAction()
        }
        binding.timerButton.setOnClickListener {
            openTimePicker()
        }
        binding.calendarButton.setOnClickListener {
            openDatePicker()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun saveAction(){
        val name = binding.name.text.toString()
        if(taskItem == null){
            val newTask = TaskItem(name, dueTime, null)
            taskViewModel.addTaskItem(newTask)
        } else {
            taskViewModel.updateTaskitem(taskItem!!.id, name, null, null)
        }
        binding.name.setText("")
        dismiss()
    }

    private fun openTimePicker(){
        if(dueTime == null)
            dueTime = LocalTime.now()
            val listenerTime = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute -> dueTime = LocalTime.of(selectedHour, selectedMinute)
        }
        val dialog = TimePickerDialog(activity, listenerTime, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task due")
        dialog.show()
    }

    private fun openDatePicker() {
        if (dueDate == null) {
            dueDate = LocalDate.now()
            val listenerDate =
                DatePickerDialog.OnDateSetListener {_, selectedDay, selectedMonth, selectedYear ->
                    dueDate = LocalDate.of(selectedDay, selectedMonth, selectedYear)
                }
            val dialog = DatePickerDialog(
                requireActivity(),
                listenerDate,
                dueDate!!.year,
                dueDate!!.monthValue,
                dueDate!!.dayOfMonth

            )
            dialog.setTitle("Deadline date")
            dialog.show()
        }
    }

}