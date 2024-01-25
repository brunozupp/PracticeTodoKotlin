package com.novelitech.practicetodokotlin.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novelitech.practicetodokotlin.databinding.ItemTodoComponentBinding
import com.novelitech.practicetodokotlin.dataclasses.Todo

/**
 * Adapter class can be understood being a class where I do the link between .xml and the data
 */
class TodoAdapter(
    var todos: MutableList<Todo>,
    val onRemove: (Int) -> Unit,
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
            holder.binding.icTrash.setOnClickListener {

                onRemove(position)
                //todos[position].onRemove(position)
            }
            /**
             * I recommend that not to use checkBox.setOnCheckedChangeListener in
             * RecyclerView.Adapter. Because on scrolling RecyclerView,
             * checkBox.setOnCheckedChangeListener will be fired by adapter. It's not safe. Instead,
             * use checkBox.setOnClickListener to interact with user inputs.
             */
            holder.binding.cbDone.setOnClickListener {
                val isChecked = holder.binding.cbDone.isChecked
                todos[position].onCheck(isChecked)
            }
        }
    }

    override fun getItemCount(): Int = todos.size
}