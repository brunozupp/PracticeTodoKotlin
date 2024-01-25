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

    private val adapter = TodoAdapter(
        todoList
    ) { position -> removeItem(position) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        repository = TodoRepository(this, binding)

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

        val todo = buildTodo(
            title = title,
            isDone = isDone,
            id = id,
        )

        todoList.add(todo)

        adapter.notifyItemInserted(todoList.size - 1)

        binding.etTitle.text.clear()
    }

    private fun buildTodo(id: UUID, isDone: Boolean, title: String) : Todo {
        return Todo(
            title = title,
            done = isDone,
            id = id,
            onRemove = { position -> removeItem(position) },
            onCheck = {checked -> onCheck(id, checked)}
        )
    }

    private fun removeItem(position: Int) {

        val dialogRemoveItem = AlertDialog.Builder(this)
            .setTitle("Are you sure that you want to delete this?")
            .setMessage("This action is irreversible! ${todoList[position].title}")
            .setPositiveButton("Yes") {_, _ ->

                todoList.removeAt(position)

                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position,todoList.size);

                repository.save(todoList)
            }
            .setNegativeButton("No") {_, _ -> true}
            .create()

        dialogRemoveItem.show()

    }

    private fun fetchDataFromLocalStorage() {

        val items = repository.getAll()

        if(items.isNotEmpty()) {

            todoList.addAll(items.map { item ->
                buildTodo(id = item.id, isDone = item.done, title = item.title)
            })

            adapter.notifyItemRangeInserted(0, todoList.size)

            //adapter.notifyDataSetChanged()
        }
    }

    private fun onCheck(id: UUID, checked: Boolean) {

        val index = todoList.indexOfFirst { item -> item.id == id }

        todoList[index] = todoList[index].copy(
            done = checked
        )

        adapter.notifyItemChanged(index)

        repository.save(todoList)
    }
}