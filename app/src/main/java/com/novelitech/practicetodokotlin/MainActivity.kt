package com.novelitech.practicetodokotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.novelitech.practicetodokotlin.adapters.TodoAdapter
import com.novelitech.practicetodokotlin.databinding.ActivityMainBinding
import com.novelitech.practicetodokotlin.dataclasses.Todo
import com.novelitech.practicetodokotlin.repositories.todo_repository.ITodoRepository
import com.novelitech.practicetodokotlin.repositories.todo_repository.TodoRepository
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var repository: ITodoRepository

    private var todoList = mutableListOf<Todo>()

    private val adapter = TodoAdapter(todoList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        repository = TodoRepository(this)

        setContentView(binding.root)

        binding.rvItems.adapter = adapter
        binding.rvItems.layoutManager = LinearLayoutManager(this)

        fetchDataFromLocalStorage()

        binding.btnAddContent.setOnClickListener {
            addNewItem()

            repository.save(todoList)
        }
    }

    private fun addNewItem() {
        val title = binding.etTitle.text.toString()
        val isDone = false
        val id = UUID.randomUUID()

        val todo = Todo(
            title = title,
            done = isDone,
            id = id,
            onRemove = { removeItem(id) }
        )

        todoList.add(todo)

        adapter.notifyItemInserted(todoList.size - 1)

        binding.etTitle.text.clear()
    }

    private fun removeItem(id: UUID) {

        val dialogRemoveItem = AlertDialog.Builder(this)
            .setTitle("Are you sure that you want to delete this?")
            .setMessage("This action is irreversible!")
            .setPositiveButton("Yes") {_, _ ->
                val index = todoList.indexOfFirst { item -> item.id == id }

                todoList.removeAt(index)

                adapter.notifyItemRemoved(index)
            }
            .setNegativeButton("No") {_, _ -> true}
            .create()

        dialogRemoveItem.show()

    }

    private fun fetchDataFromLocalStorage() {

        val items = repository.getAll()

        if(items.isNotEmpty()) {
            todoList.addAll(items)

            // In this case where I will set ALL the items in the list at once, it's OK to use
            // this function.
            adapter.notifyDataSetChanged()
        }
    }
}