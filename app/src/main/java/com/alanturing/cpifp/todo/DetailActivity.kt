package com.alanturing.cpifp.todo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityDetailBinding
import com.alanturing.cpifp.todo.model.Task

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val repository = TaskLocalRepository.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val task:Task? = intent?.extras?.getParcelable("TASK")
        var id = 0
        var title = ""
        var description = ""
        var check = false

        if (task!=null) {
            title = task.title.toString()
            description = task.description.toString()
            id = task.id
            binding.isCompleted.isChecked = task.isCompleted
        }

        binding.titleInput.setText(title)
        binding.descriptionInput.setText(description)

        binding.submitButton.setOnClickListener {
            var task = Task(id, binding.titleInput.text.toString(), binding.descriptionInput.text.toString(), binding.isCompleted.isChecked)
            repository.update(task)
            setResult(Activity.RESULT_OK)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}