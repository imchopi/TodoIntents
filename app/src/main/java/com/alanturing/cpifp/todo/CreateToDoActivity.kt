package com.alanturing.cpifp.todo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alanturing.cpifp.todo.data.TaskLocalRepository
import com.alanturing.cpifp.todo.databinding.ActivityCreateToDoBinding
import com.alanturing.cpifp.todo.model.Task

class CreateToDoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateToDoBinding
    private val repository = TaskLocalRepository.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.createButton.setOnClickListener {
            var task = Task(-1, binding.titleInput.text.toString(), binding.descriptionInput.text.toString())
            getTaskId()
            repository.add(task)
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

    }
}