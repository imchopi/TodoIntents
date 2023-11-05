package com.alanturing.cpifp.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.alanturing.cpifp.todo.adapter.TasksAdapter
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityMainBinding
import com.alanturing.cpifp.todo.model.Task
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val repository = TaskLocalRepository.getInstance()
    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        when (it.resultCode) {
            Activity.RESULT_OK -> {
                val taskAdapter = TasksAdapter(repository.tasks, ::onShareItem , ::onEdit)
                binding.tasks.adapter = taskAdapter
                //Snackbar.make(this, binding.root, "Tarea creada", Snackbar.LENGTH_SHORT).show()
            }
            Activity.RESULT_CANCELED -> {
                //Toast.makeText(this,"Se ha cancelado", Toast.LENGTH_SHORT).show()
                Snackbar.make(this, binding.root, "Tarea cancelada", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskAdapter = TasksAdapter(repository.tasks, ::onShareItem , ::onEdit)
        binding.tasks.adapter = taskAdapter

        binding.floatingButton.setOnClickListener {
            val intent = Intent(this, CreateToDoActivity::class.java)
            getResult.launch(intent)
        }

    }

    fun onShareItem(task: Task, view:View) {
        val statusText = if (task.isCompleted)
            "completed"
        else
            "pendent"

        val shareText = getString(
            R.string.share_text,
            task.title,
            task.description,
            statusText)

        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent,null)
        startActivity(shareIntent)
    }

    fun onEdit(task: Task, view:View) {
        val editIntent = Intent(this, DetailActivity::class.java)
        editIntent.putExtra("TASK", task)
        startActivity(editIntent)
    }
}