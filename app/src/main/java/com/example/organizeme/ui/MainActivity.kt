package com.example.organizeme.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.organizeme.R
import com.example.organizeme.databinding.ActivityMainBinding
import com.example.organizeme.databinding.FragmentToDoBinding
import com.example.organizeme.databinding.TaskLayoutBinding
import com.example.organizeme.fragment.HomeFragment
import com.example.organizeme.fragment.PomodoroFragment
import com.example.organizeme.fragment.ProfileFragment
import com.example.organizeme.fragment.ToDoFragment
import com.example.organizeme.helper.TaskItemAdapter
import com.example.organizeme.helper.ToDoModel
import com.example.organizeme.helper.ToDoNewTask
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentToDoBinding
    private lateinit var taskViewModel: ToDoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupWindowFeatures()

        // actionBar?.show() // Remove this line if you want to show the action bar
        setContentView(R.layout.activity_main)

        binding = FragmentToDoBinding.inflate(layoutInflater)

        setupBottomNavigation()
        setupFloatingActionButton()
        loadFragment(HomeFragment())

        taskViewModel = ViewModelProvider(this).get(ToDoModel::class.java)
        setRecyclerView()
    }

    private fun setupWindowFeatures() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun setupBottomNavigation() {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavBar)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome -> loadFragment(HomeFragment())
                R.id.navList -> loadFragment(ToDoFragment())
                R.id.navTime -> loadFragment(PomodoroFragment())
                R.id.navProfile -> loadFragment(ProfileFragment())
            }
            true
        }
    }

    private fun setupFloatingActionButton() {
        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            // Show the bottom sheet or dialog to add a new task
            ToDoNewTask(null).show(supportFragmentManager, "newTaskTag")
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun setRecyclerView() {
        taskViewModel.taskItems.observe(this) { taskItems ->
            binding.taskRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(taskItems)
            }
        }
    }
}
