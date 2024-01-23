package com.novelitech.practicetodokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelitech.practicetodokotlin.adapters.TodoAdapter
import com.novelitech.practicetodokotlin.databinding.ActivityMainBinding
import com.novelitech.practicetodokotlin.dataclasses.Todo
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var todoList = mutableListOf<Todo>()

        val adapter = TodoAdapter(todoList)

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)

        binding.btnAddContent.setOnClickListener {

            val title = binding.etTitle.text.toString()
            val isDone = false
            val id = UUID.randomUUID()

            val todo = Todo(
                title = title,
                done = isDone,
                id = id
            )

            todoList.add(todo)

            adapter.notifyItemInserted(todoList.size - 1)

            binding.etTitle.text.clear()
        }
    }
}