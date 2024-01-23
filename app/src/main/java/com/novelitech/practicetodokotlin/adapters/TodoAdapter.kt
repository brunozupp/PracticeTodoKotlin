package com.novelitech.practicetodokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novelitech.practicetodokotlin.databinding.ItemTodoComponentBinding
import com.novelitech.practicetodokotlin.dataclasses.Todo

/**
 * Adapter class can be understood being a class where I do the link between .xml and the data
 */
class TodoAdapter(
    var todos: List<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /**
     * A nested class marked as inner can access the members of its outer class.
     * Inner classes carry a reference to an object of an outer class.
     */
    inner class TodoViewHolder(val binding: ItemTodoComponentBinding) : RecyclerView.ViewHolder(binding.root)

    // It's called when the RecyclerView needs a new Holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {

        val binding = ItemTodoComponentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return TodoViewHolder(binding)
    }

    // Bind the data to the items/views
    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        holder.itemView.apply {
            holder.binding.tvTitle.text = todos[position].title
            holder.binding.cbDone.isChecked = todos[position].done
            holder.binding.tvId.text = todos[position].id.toString()
        }
    }

    override fun getItemCount(): Int = todos.size
}